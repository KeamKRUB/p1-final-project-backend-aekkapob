package ku.cs.project_SE.entity.promotion.promotion_condition.builder;

import ku.cs.project_SE.dto.promotion.condition.ConditionSpecDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.HouseInCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.ProductCategoryCondition;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.PromotionCondition;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductCategoryConditionBuilder implements ConditionBuilder {

    @Override
    public String type() {
        return "PRODUCT_CATEGORY";
    }

    @Override
    public PromotionCondition build(Promotion promotion,
                                    CompositeCondition parent,
                                    ConditionSpecDto spec) {
        Object v = spec.params().get("categoryId");
        if (!(v instanceof String s) || s.isBlank()) {
            throw new IllegalArgumentException("categoryId is required");
        }

        ProductCategoryCondition c = ProductCategoryCondition.builder()
                .categoryId(s)
                .build();

        c.setPromotion(promotion);
        c.setParentComposite(parent);
        return c;
    }
    @Override
    public ConditionSpecDto toSpec(PromotionCondition entity) {
        ProductCategoryCondition c = (ProductCategoryCondition) entity;
        return new ConditionSpecDto(
                type(),
                Map.of("categoryId", c.getCategoryId()),null
        );
    }
    @Override
    public boolean support(PromotionCondition condition) {
        return condition instanceof ProductCategoryCondition;
    }
}
