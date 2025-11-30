package ku.cs.project_SE.mapper;

import ku.cs.project_SE.dto.user.UserRequestDto;
import ku.cs.project_SE.dto.user.UserRespondDto;
import ku.cs.project_SE.dto.verifyUser.VerifyUserDataDto;
import ku.cs.project_SE.entity.user.Role;
import ku.cs.project_SE.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper (
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    // Entity → DTO
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email",target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "role", target = "role", qualifiedByName = "roleToString")
    UserRespondDto toDto(User entity);

//    @Mapping(source = "userId", target = "userId")
//    @Mapping(source = "firstName", target = "firstName")
//    @Mapping(source = "lastName", target = "lastName")
//    @Mapping(source = "email",target = "email")
//    @Mapping(source = "phoneNumber", target = "phoneNumber")
//    @Mapping(source = "role", target = "role", qualifiedByName = "roleToString")
//    VerifyUserDataDto toVerifyDto(User entity);

    // DTO → Entity
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "first", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "role", target = "role", qualifiedByName = "stringToRole")
    User toEntity(UserRequestDto dto);

    // Converter: Role → String
    @Named("roleToString")
    default String roleToString(Role role) {
        return role != null ? role.name() : null;
    }

    // Converter: String → Role
    @Named("stringToRole")
    default Role stringToRole(String role) {
        return role != null ? Role.valueOf(role) : null;
    }
}