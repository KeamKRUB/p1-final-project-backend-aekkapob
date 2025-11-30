package ku.cs.project_SE.service;

import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_action.FixedAmountDiscountAction;
import ku.cs.project_SE.entity.promotion.promotion_action.PercentageDiscountAction;
import ku.cs.project_SE.entity.promotion.promotion_action.PromotionAction;
import ku.cs.project_SE.repository.promotion.FixedAmountDiscountActionRepository;
import ku.cs.project_SE.repository.promotion.PercentageDiscountActionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ActionResolverService {

    private final PercentageDiscountActionRepository pctRepo;
    private final FixedAmountDiscountActionRepository fixedRepo;

    public ActionResolverService(PercentageDiscountActionRepository pctRepo,
                                 FixedAmountDiscountActionRepository fixedRepo) {
        this.pctRepo = pctRepo;
        this.fixedRepo = fixedRepo;
    }

    public PromotionAction resolve(Promotion promotion) {
        if (promotion == null) return null;
        String id = promotion.getPromotionId();
        PercentageDiscountAction p = pctRepo.findByPromotion_PromotionId(id).orElse(null);
        if (p != null) return p;
        FixedAmountDiscountAction f = fixedRepo.findByPromotion_PromotionId(id).orElse(null);
        if (f != null) return f;
        return p;
    }
}
