package ku.cs.project_SE.dto.user;

import ku.cs.project_SE.entity.user.Role;

public record UserRespondDto(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Role role

) {

}
