package ku.cs.project_SE.entity.promotion.promotion_action;

import ku.cs.project_SE.dto.promotion.action.ActionDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_action.builder.ActionBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ActionFactory {
    private final Map<String, ActionBuilder> builders;
    public ActionFactory(java.util.List<ActionBuilder> list) {
        this.builders = list.stream().collect(Collectors.toUnmodifiableMap(ActionBuilder::type, b -> b));
    }
    public PromotionAction build(String type, Promotion p, java.util.Map<String,Object> params) {
        var b = builders.get(type);
        if (b == null) throw new IllegalArgumentException("Unsupported action type: " + type);
        return b.build(p, params);
    }

    public ActionDto toDto(PromotionAction action) {
        return builders.values().stream()
                .filter(b -> b.support(action))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported action entity: " + action.getClass()))
                .toDto(action);
    }
}
