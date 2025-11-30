package ku.cs.project_SE.service;

import ku.cs.project_SE.dto.PageResponseDto;
import ku.cs.project_SE.dto.promotion.CreatePromotionDto;
import ku.cs.project_SE.dto.promotion.PromotionResponseDto;
import ku.cs.project_SE.dto.promotion.UpdatePromotionDto;
import ku.cs.project_SE.dto.promotion.action.ActionDto;
import ku.cs.project_SE.dto.promotion.action.SetActionRequest;
import ku.cs.project_SE.dto.promotion.condition.AddCompositeConditionRequest;
import ku.cs.project_SE.dto.promotion.condition.ConditionSpecDto;
import ku.cs.project_SE.dto.promotion.validate.PromotionValidationResult;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_action.*;
import ku.cs.project_SE.entity.promotion.promotion_condition.ConditionFactory;
import ku.cs.project_SE.entity.promotion.promotion_condition.LogicalOperator;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.PromotionCondition;
import ku.cs.project_SE.mapper.PromotionMapper;
import ku.cs.project_SE.repository.*;
import ku.cs.project_SE.repository.promotion.FixedAmountDiscountActionRepository;
import ku.cs.project_SE.repository.promotion.PercentageDiscountActionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PromotionService {

    private final PromotionRepository repository;
    private final PromotionMapper mapper;
    private final CompositeConditionRepository compositeRepo;
    private final ConditionFactory conditionFactory;
    private final HouseRepository houseRepository;
    private final PercentageDiscountActionRepository pctRepo;
    private final FixedAmountDiscountActionRepository fixedRepo;
    private final ActionFactory actionFactory;

    public PromotionService(PromotionRepository repository,
                            PromotionMapper mapper,
                            CompositeConditionRepository compositeRepo,
                            ConditionFactory conditionFactory,
                            HouseRepository houseRepository,
                            PercentageDiscountActionRepository pctRepo,
                            FixedAmountDiscountActionRepository fixedRepo,
                            ActionFactory actionFactory) {
        this.repository = repository;
        this.mapper = mapper;
        this.compositeRepo = compositeRepo;
        this.conditionFactory = conditionFactory;
        this.houseRepository = houseRepository;
        this.pctRepo = pctRepo;
        this.fixedRepo = fixedRepo;
        this.actionFactory = actionFactory;
    }

    public PageResponseDto<PromotionResponseDto> search(String query, String filter, int page, int pageSize) {
        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "promotionId"));
        String q = (query == null || query.isBlank()) ? null : query.trim();
        Boolean active = parseActive(filter);
        Page<Promotion> resultPage;
        if (q == null && active == null) {
            resultPage = repository.findAll(pageable);
        } else if (active != null && q != null) {
            resultPage = repository.searchWithFilter(q, active, pageable);
        } else if (active != null) {
            resultPage = repository.searchWithFilter("", active, pageable);
        } else {
            resultPage = repository.search(q, pageable);
        }
        List<PromotionResponseDto> items = resultPage.getContent().stream().map(mapper::toDto).toList();
        return new PageResponseDto<>(items, page, pageSize, resultPage.getTotalElements(), resultPage.getTotalPages());
    }

    public PromotionResponseDto getById(String id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public PromotionResponseDto create(CreatePromotionDto req) {
        Promotion entity = mapper.toEntity(req);
        return mapper.toDto(repository.save(entity));
    }

    public PromotionResponseDto update(String id, UpdatePromotionDto req) {
        Promotion entity = repository.findById(id).orElseThrow();
        mapper.applyUpdate(req, entity);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(String id) {
        if (repository.existsById(id)) repository.deleteById(id);
    }

    @Transactional
    public Long addCompositeCondition(String promotionId, AddCompositeConditionRequest req) {
        Promotion p = repository.findById(promotionId).orElseThrow();

        compositeRepo.deleteAll(compositeRepo.findByPromotion_PromotionId(promotionId));

        CompositeCondition root = new CompositeCondition();
        root.setPromotion(p);
        root.setOperator(req.operator());

        if (req.conditions() != null && !req.conditions().isEmpty()) {
            CompositeCondition currentGroup = root;
            LogicalOperator currentOp = req.operator();

            for (ConditionSpecDto spec : req.conditions()) {
                LogicalOperator nextOp = spec.operator() != null ? spec.operator() : currentOp;

                if (nextOp != currentOp) {
                    CompositeCondition newGroup = new CompositeCondition();
                    newGroup.setPromotion(p);
                    newGroup.setOperator(nextOp);
                    root.addChild(newGroup);
                    currentGroup = newGroup;
                    currentOp = nextOp;
                }

                PromotionCondition cond = conditionFactory.fromSpec(p, currentGroup, spec);
                currentGroup.addChild(cond);
            }
        }

        CompositeCondition saved = compositeRepo.save(root);
        return saved.getId();
    }


    public boolean check(String promotionId, Long houseId) {
        House house = houseRepository.findById(houseId).orElseThrow();
        List<CompositeCondition> list = compositeRepo.findByPromotion_PromotionId(promotionId);
        if (list.isEmpty()) return true;
        for (CompositeCondition cc : list) {
            if (cc.evaluate(house)) return true;
        }
        return false;
    }

    @Transactional
    public void setAction(String promotionId, SetActionRequest req) {
        Promotion p = repository.findById(promotionId).orElseThrow();

        p.setFixedAction(null);
        p.setPercentageAction(null);

        repository.flush();

        PromotionAction action = actionFactory.build(req.type(), p, req.params());

        if (action instanceof PercentageDiscountAction a) {
            p.setPercentageAction(a);
        } else if (action instanceof FixedAmountDiscountAction a) {
            p.setFixedAction(a);
        } else {
            throw new IllegalArgumentException("Unsupported action instance");
        }
    }


    private Boolean parseActive(String filter) {
        if (filter == null || filter.isBlank()) return null;
        String f = filter.trim().toLowerCase();
        if (f.equals("true") || f.equals("1") || f.equals("active")) return Boolean.TRUE;
        if (f.equals("false") || f.equals("0") || f.equals("inactive")) return Boolean.FALSE;
        return null;
    }
    public List<ConditionSpecDto> getConditions(String promotionId) {
        var list = compositeRepo.findByPromotion_PromotionId(promotionId);
        return list.stream()
                .flatMap(cc -> cc.getConditions().stream())
                .map(conditionFactory::toSpec)
                .toList();
    }

    public ActionDto getAction(String promotionId) {
        Promotion p = repository.findById(promotionId).orElseThrow();
        if (p.getFixedAction() != null) return actionFactory.toDto(p.getFixedAction());
        if (p.getPercentageAction() != null) return actionFactory.toDto(p.getPercentageAction());
        return null;
    }

    public PromotionValidationResult validatePromotion(Long houseId, String code) {
        Promotion promotion = repository.findByPromotionName(code).orElse(null);
        if (promotion == null || !promotion.isActive()) return new PromotionValidationResult(false, "ไม่พบโค้ดโปรโมชันหรือหมดอายุ", null, 0);

        House house = houseRepository.findById(houseId).orElseThrow();
        boolean passed = check(promotion.getPromotionId(), houseId);
        System.out.println(promotion.getConditions().get(0).getConditions());
        if (!passed) return new PromotionValidationResult(false, "บ้านนี้ไม่เข้าเงื่อนไขโปรโมชัน", null, house.getPrice());

        ActionDto actionDto = getAction(promotion.getPromotionId());
        double finalPrice = house.getPrice();

        if (promotion.getPercentageAction() != null) {
            PercentageDiscountAction a = promotion.getPercentageAction();
            double discount = house.getPrice() * (a.getPercentage() / 100.0);
            if (a.getMaxDiscount() != null && a.getMaxDiscount() > 0)
                discount = Math.min(discount, a.getMaxDiscount());
            finalPrice = Math.max(0, house.getPrice() - discount);
        }
        else if (promotion.getFixedAction() != null) {
            FixedAmountDiscountAction a = promotion.getFixedAction();
            finalPrice = Math.max(0, house.getPrice() - a.getDiscountAmount());
        }

        return new PromotionValidationResult(true, "ใช้โค้ดได้", actionDto, finalPrice);
    }
}
