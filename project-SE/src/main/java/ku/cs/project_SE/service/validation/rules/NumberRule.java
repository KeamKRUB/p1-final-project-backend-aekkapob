package ku.cs.project_SE.service.validation.rules;

import ku.cs.project_SE.service.validation.PasswordRule;
import ku.cs.project_SE.service.validation.PasswordValidationContext;

public class NumberRule implements PasswordRule {
    @Override
    public String key() { return "number"; }

    @Override
    public String message() { return "ต้องมีตัวเลข (0-9)"; }

    @Override
    public boolean validate(String password, PasswordValidationContext context) {

        return password.chars().anyMatch(Character::isDigit);
    }
}
