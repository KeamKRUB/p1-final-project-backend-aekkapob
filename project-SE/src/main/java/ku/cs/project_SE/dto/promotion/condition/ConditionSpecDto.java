package ku.cs.project_SE.dto.promotion.condition;

import ku.cs.project_SE.entity.promotion.promotion_condition.LogicalOperator;

import java.util.Map;

public record ConditionSpecDto(
        String type,
        Map<String, Object> params,
        LogicalOperator operator
) {}
