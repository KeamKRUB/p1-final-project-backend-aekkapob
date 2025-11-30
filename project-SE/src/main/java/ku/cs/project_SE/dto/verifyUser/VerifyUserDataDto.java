package ku.cs.project_SE.dto.verifyUser;

import java.util.UUID;

public record VerifyUserDataDto(
        UUID userId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
