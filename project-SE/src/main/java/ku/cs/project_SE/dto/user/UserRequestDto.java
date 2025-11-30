package ku.cs.project_SE.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRequestDto {
    private UUID id;
    private String first;
    private String lastname;
    private String phone;
    private String email;
    private String password;
    private String role;
}
