package ku.cs.project_SE.entity.promotion.promotion_action.builder;

import ku.cs.project_SE.dto.promotion.action.ActionDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_action.FixedAmountDiscountAction;
import ku.cs.project_SE.entity.promotion.promotion_action.PercentageDiscountAction;
import ku.cs.project_SE.entity.promotion.promotion_action.PromotionAction;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PercentageDiscountActionBuilder implements ActionBuilder {
    @Override
    public String type() { return "PERCENTAGE"; }

    @Override
    public PromotionAction build(Promotion promotion, Map<String, Object> params) {
        Object v = params.get("percentage");
        double pct = (v instanceof Number n) ? n.doubleValue() : 0.0;

        Object m = params.get("maxDiscount");
        Double max = (m instanceof Number n) ? n.doubleValue() : null;

        return PercentageDiscountAction.builder()
                .promotion(promotion)
                .promotionId(promotion.getPromotionId())
                .percentage(pct)
                .maxDiscount(max)
                .build();
    }

    @Override
    public ActionDto toDto(PromotionAction action) {
        PercentageDiscountAction a = (PercentageDiscountAction) action;
        return new ActionDto(
                type(),
                Map.of(
                        "percentage", a.getPercentage(),
                        "maxDiscount", a.getMaxDiscount()
                )
        );
    }

    @Override
    public boolean support(PromotionAction action) {
        return action instanceof PercentageDiscountAction;
    }
}

