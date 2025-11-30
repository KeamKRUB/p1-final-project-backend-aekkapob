package ku.cs.project_SE.dto.house_register;

import ku.cs.project_SE.dto.house.HouseDetailDto;
import ku.cs.project_SE.dto.user.UserRequestDto;
import ku.cs.project_SE.dto.user.UserRespondDto;
import ku.cs.project_SE.enums.HouseRegisterStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record HouseRegisterRespondDto(
        UUID registerId, HouseDetailDto houseDetailDto, UserRespondDto userRespondDto, LocalDateTime date,
        HouseRegisterStatus status) {
}
