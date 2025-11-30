package ku.cs.project_SE.entity.promotion.promotion_action.builder;

import ku.cs.project_SE.dto.promotion.action.ActionDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_action.FixedAmountDiscountAction;
import ku.cs.project_SE.entity.promotion.promotion_action.PromotionAction;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FixedAmountDiscountActionBuilder implements ActionBuilder {
    @Override public String type() { return "FIXED"; }

    @Override
    public PromotionAction build(Promotion promotion, Map<String, Object> params) {
        Object v = params.get("amount");
        if (v == null) v = params.get("discountAmount");
        double amt = (v instanceof Number n) ? n.doubleValue() : 0.0;

        return FixedAmountDiscountAction.builder()
                .promotion(promotion)
                .promotionId(promotion.getPromotionId())
                .discountAmount(amt)
                .build();
    }

    @Override
    public ActionDto toDto(PromotionAction action) {
        FixedAmountDiscountAction a = (FixedAmountDiscountAction) action;
        return new ActionDto(
                type(),
                Map.of("amount", a.getDiscountAmount())
        );
    }

    @Override
    public boolean support(PromotionAction action) {
        return action instanceof FixedAmountDiscountAction;
    }

}

