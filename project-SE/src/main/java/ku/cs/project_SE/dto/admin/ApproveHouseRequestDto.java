package ku.cs.project_SE.dto.admin;

import java.util.UUID;

public record ApproveHouseRequestDto(
        UUID requestId,
        String adminComment
) {
}

