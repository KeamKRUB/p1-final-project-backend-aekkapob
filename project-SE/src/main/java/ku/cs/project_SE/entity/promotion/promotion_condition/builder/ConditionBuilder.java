// keam
package ku.cs.project_SE.entity.promotion.promotion_condition.builder;

import ku.cs.project_SE.dto.promotion.condition.ConditionSpecDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.PromotionCondition;

public interface ConditionBuilder {
    String type();
    PromotionCondition build(Promotion promotion, CompositeCondition parent, ConditionSpecDto spec);
    ConditionSpecDto toSpec(PromotionCondition entity);
    boolean support(PromotionCondition condition);
}
