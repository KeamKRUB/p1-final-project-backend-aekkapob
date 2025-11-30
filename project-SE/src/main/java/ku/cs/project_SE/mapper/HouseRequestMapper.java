package ku.cs.project_SE.mapper;

import ku.cs.project_SE.dto.house.HouseRequestDto;
import ku.cs.project_SE.dto.user.UserDataDto;
import ku.cs.project_SE.entity.house.HouseRequest;
import ku.cs.project_SE.entity.user.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@SuppressWarnings({"java:S6813"})
public abstract class HouseRequestMapper {

    // MapStruct requires field injection for dependencies in abstract mappers.
    // Constructor injection is not supported as MapStruct generates implementation classes.
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected HouseMapper houseMapper;

    public HouseRequestDto toDto(HouseRequest entity) {
        if (entity == null) return null;

        return new HouseRequestDto(
                entity.getRequestId(),
                toUserDataDto(entity.getRequester()),
                houseMapper.toDetail(entity.getHouse()),
                entity.getStatus(),
                entity.getSubmittedAt(),
                entity.getReviewedAt(),
                entity.getAdminComment()
        );
    }

    protected UserDataDto toUserDataDto(User user) {
        if (user == null) return null;
        return new UserDataDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
