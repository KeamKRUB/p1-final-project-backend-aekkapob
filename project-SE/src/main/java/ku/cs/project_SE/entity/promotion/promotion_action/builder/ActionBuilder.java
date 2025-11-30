package ku.cs.project_SE.entity.promotion.promotion_action.builder;

import ku.cs.project_SE.dto.promotion.action.ActionDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_action.PromotionAction;

import java.util.Map;

public interface ActionBuilder {
    String type();

    PromotionAction build(Promotion promotion, Map<String, Object> params);

    ActionDto toDto(PromotionAction action);

    boolean support(PromotionAction action);
}
