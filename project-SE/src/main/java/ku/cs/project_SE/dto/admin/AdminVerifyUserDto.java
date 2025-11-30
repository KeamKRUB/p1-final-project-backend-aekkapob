package ku.cs.project_SE.dto.admin;

import java.util.UUID;

public record AdminVerifyUserDto(
        UUID requestId,
        String reason
) {}
