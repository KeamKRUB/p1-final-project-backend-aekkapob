// keam
package ku.cs.project_SE.dto.promotion;

import java.time.Instant;

public record PromotionResponseDto(
        String promotionId,
        String promotionName,
        String description,
        boolean isActive,
        Integer usageLimit,
        Integer usedCount,
        Instant createdAt,
        Instant updatedAt
) {}
