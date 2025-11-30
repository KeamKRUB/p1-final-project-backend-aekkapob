package ku.cs.project_SE.dto.promotion.action;

import java.util.Map;

public record ActionDto(
        String type,
        Map<String, Object> params
) {}
