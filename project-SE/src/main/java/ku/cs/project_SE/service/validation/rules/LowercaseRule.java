package ku.cs.project_SE.service.validation.rules;

import ku.cs.project_SE.service.validation.PasswordRule;
import ku.cs.project_SE.service.validation.PasswordValidationContext;

public class LowercaseRule implements PasswordRule {
    @Override
    public String key() { return "lowercase"; }

    @Override
    public String message() { return "ต้องมีตัวอักษรพิมพ์เล็ก (a-z)"; }

    @Override
    public boolean validate(String password, PasswordValidationContext context) {
        return password.chars().anyMatch(Character::isLowerCase);
    }
}
