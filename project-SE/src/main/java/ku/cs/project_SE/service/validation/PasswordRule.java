package ku.cs.project_SE.service.validation;

public interface PasswordRule {
    String key();
    String message();
    boolean validate(String password, PasswordValidationContext context);
}

