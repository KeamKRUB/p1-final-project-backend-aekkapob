package ku.cs.project_SE.mapper;


import ku.cs.project_SE.dto.verifyUser.VerifyUserRequestDto;
import ku.cs.project_SE.dto.verifyUser.VerifyUserResponseDto;
import ku.cs.project_SE.entity.VerifyUser.VerifyUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface VerifyUserMapper {

    VerifyUser toEntity(VerifyUserRequestDto dto);

    @Mapping(target = "user", source = "requester")
    VerifyUserResponseDto toDto(VerifyUser entity);
}
