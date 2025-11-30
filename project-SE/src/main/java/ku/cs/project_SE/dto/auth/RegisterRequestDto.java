package ku.cs.project_SE.dto.auth;

public record RegisterRequestDto(
        String email,
        String phoneNumber,
        String firstName,
        String lastName,
        String password,
        String confirmPassword
) {
}
