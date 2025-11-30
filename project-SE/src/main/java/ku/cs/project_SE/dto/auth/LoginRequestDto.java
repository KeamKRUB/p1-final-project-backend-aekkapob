package ku.cs.project_SE.dto.auth;

public record LoginRequestDto(
        String identifier,
        String password
) {

}
