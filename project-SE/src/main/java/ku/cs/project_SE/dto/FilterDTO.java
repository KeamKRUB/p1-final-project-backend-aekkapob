package ku.cs.project_SE.dto;

import java.util.List;
import java.util.Map;

public record FilterDTO (
    int filterCount,
    List<Map<String, Object>> filters
) {}
