package ku.cs.project_SE.entity.promotion.promotion_condition.builder;

import ku.cs.project_SE.dto.promotion.condition.ConditionSpecDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.DateRangeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.HouseInCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.PromotionCondition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HouseInConditionBuilder implements ConditionBuilder {
    @Override
    public String type() { return "HOUSE_IN"; }

    @Override
    public PromotionCondition build(Promotion promotion,
                                    CompositeCondition parent,
                                    ConditionSpecDto spec) {
        Object v = spec.params().get("houseIds");
        if (!(v instanceof List<?> list) || list.isEmpty()) {
            throw new IllegalArgumentException("houseIds is required");
        }
        List<Long> ids = list.stream().map(o -> Long.valueOf(o.toString())).toList();

        HouseInCondition c = HouseInCondition.builder().houseIds(ids).build();
        c.setPromotion(promotion);
        c.setParentComposite(parent);
        return c;
    }
    @Override
    public ConditionSpecDto toSpec(PromotionCondition entity) {
        HouseInCondition c = (HouseInCondition) entity;
        return new ConditionSpecDto(
                type(),
                Map.of("houseIds", c.getHouseIds()), null
        );
    }
    @Override
    public boolean support(PromotionCondition condition) {
        return condition instanceof HouseInCondition;
    }
}