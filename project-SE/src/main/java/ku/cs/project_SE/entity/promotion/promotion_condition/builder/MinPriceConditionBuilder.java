// keam
package ku.cs.project_SE.entity.promotion.promotion_condition.builder;

import ku.cs.project_SE.dto.promotion.condition.ConditionSpecDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.HouseInCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.MinimumPriceCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.PromotionCondition;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MinPriceConditionBuilder implements ConditionBuilder {
    @Override public String type() { return "MINIMUM_PRICE"; }

    @Override
    public PromotionCondition build(Promotion promotion, CompositeCondition parent, ConditionSpecDto spec) {
        double min = (spec.params().get("minimumPrice") instanceof Number n) ? n.doubleValue() : 0.0;
        var c = MinimumPriceCondition.builder().minimumPrice(min).build();
        c.setPromotion(promotion);
        c.setParentComposite(parent);
        return c;
    }

    @Override
    public ConditionSpecDto toSpec(PromotionCondition entity) {
        MinimumPriceCondition c = (MinimumPriceCondition) entity;
        return new ConditionSpecDto(
                type(),
                Map.of("minimumPrice", c.getMinimumPrice()),null
        );
    }

    @Override
    public boolean support(PromotionCondition condition) {
        return condition instanceof MinimumPriceCondition;
    }
}
