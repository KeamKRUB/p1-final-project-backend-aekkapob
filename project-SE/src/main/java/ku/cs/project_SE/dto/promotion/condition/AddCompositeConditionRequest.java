package ku.cs.project_SE.dto.promotion.condition;

import ku.cs.project_SE.entity.promotion.promotion_condition.LogicalOperator;

import java.util.List;

public record AddCompositeConditionRequest(
        LogicalOperator operator,
        List<ConditionSpecDto> conditions
) {}
