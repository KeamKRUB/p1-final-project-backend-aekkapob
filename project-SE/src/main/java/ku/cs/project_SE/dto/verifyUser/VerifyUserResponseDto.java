package ku.cs.project_SE.dto.verifyUser;

import ku.cs.project_SE.dto.user.UserDataDto;
import ku.cs.project_SE.enums.VerifyUserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record VerifyUserResponseDto(
        UUID requestId,
        String citizenId,
        VerifyUserStatus requestStatus,
        String idCardImage,
        LocalDateTime requestDate,
        VerifyUserDataDto user
) {
}
