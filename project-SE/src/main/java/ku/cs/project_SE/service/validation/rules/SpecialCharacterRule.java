package ku.cs.project_SE.service.validation.rules;

import ku.cs.project_SE.service.validation.PasswordRule;
import ku.cs.project_SE.service.validation.PasswordValidationContext;

public class SpecialCharacterRule implements PasswordRule {
    @Override
    public String key() { return "special"; }

    @Override
    public String message() { return "ต้องมีอักขระพิเศษ (!@#...)"; }

    @Override
    public boolean validate(String password, PasswordValidationContext context) {
        final String specialChars = "!@#$%^&*(),.?\":{}|<>";

        return password.chars().anyMatch(c -> specialChars.indexOf(c) != -1);
    }
}
