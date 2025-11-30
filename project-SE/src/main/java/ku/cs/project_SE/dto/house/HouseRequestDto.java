package ku.cs.project_SE.dto.house;

import ku.cs.project_SE.dto.user.UserDataDto;
import ku.cs.project_SE.enums.house.HouseStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record HouseRequestDto(
        UUID requestId,
        UserDataDto user,
        HouseDetailDto house,
        HouseStatus status,
        LocalDateTime submittedAt,
        LocalDateTime reviewedAt,
        String adminComment
) {
}
