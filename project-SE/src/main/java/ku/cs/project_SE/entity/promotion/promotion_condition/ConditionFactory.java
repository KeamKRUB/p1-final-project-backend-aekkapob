package ku.cs.project_SE.entity.promotion.promotion_condition;

import ku.cs.project_SE.dto.promotion.condition.ConditionSpecDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_condition.builder.ConditionBuilder;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.PromotionCondition;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConditionFactory {

    private final Map<String, ConditionBuilder> builders;

    public ConditionFactory(java.util.List<ConditionBuilder> builderList) {
        this.builders = builderList.stream()
                .collect(Collectors.toUnmodifiableMap(ConditionBuilder::type, b -> b));
    }

    public PromotionCondition fromSpec(Promotion promotion, CompositeCondition parent, ConditionSpecDto spec) {
        ConditionBuilder b = builders.get(spec.type());
        if (b == null) {
            throw new IllegalArgumentException("Unsupported condition type: " + spec.type());
        }
        return b.build(promotion, parent, spec);
    }

    public ConditionSpecDto toSpec(PromotionCondition condition) {
        return builders.values().stream()
                .filter(b -> b.support(condition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported condition entity: " + condition.getClass()))
                .toSpec(condition);
    }

}
