package ku.cs.project_SE.dto.admin;

import java.util.UUID;

public record RejectHouseRequestDto(
        UUID requestId,
        String adminComment
) {
}
