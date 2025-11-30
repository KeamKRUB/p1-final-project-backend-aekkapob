package ku.cs.project_SE.service.validation;

public record PasswordValidationContext(
        String email,
        String firstName,
        String lastName
) {}
