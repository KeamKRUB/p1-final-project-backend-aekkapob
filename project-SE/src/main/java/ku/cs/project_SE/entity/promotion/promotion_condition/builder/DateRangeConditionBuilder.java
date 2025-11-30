package ku.cs.project_SE.entity.promotion.promotion_condition.builder;

import ku.cs.project_SE.dto.promotion.condition.ConditionSpecDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.DateRangeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.HouseInCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.PromotionCondition;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static jakarta.xml.bind.DatatypeConverter.parseDateTime;

@Component
public class DateRangeConditionBuilder implements ConditionBuilder {
    @Override
    public String type() {
        return "DATE_RANGE";
    }

    @Override
    public PromotionCondition build(Promotion promotion, CompositeCondition parent, ConditionSpecDto spec) {
        Map<String,Object> params = spec.params();
        Date s = null, e = null;
        Object sv = params.get("startDate");
        Object ev = params.get("endDate");
        if (sv instanceof String ss) s = parseDateTime(ss).getTime();
        if (ev instanceof String ee) e = parseDateTime(ee).getTime();

        DateRangeCondition c = DateRangeCondition.builder()
                .startDate(s)
                .endDate(e)
                .build();
        c.setPromotion(promotion);
        c.setParentComposite(parent);
        return c;
    }

    @Override
    public ConditionSpecDto toSpec(PromotionCondition entity) {
        DateRangeCondition c = (DateRangeCondition) entity;
        return new ConditionSpecDto(type(),
                Map.of("startDate", c.getStartDate().toString(),
                        "endDate", c.getEndDate().toString()), null);
    }

    @Override
    public boolean support(PromotionCondition condition) {
        return condition instanceof DateRangeCondition;
    }
}
