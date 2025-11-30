package ku.cs.project_SE.service.validation.rules;

import ku.cs.project_SE.service.validation.PasswordRule;
import ku.cs.project_SE.service.validation.PasswordValidationContext;

public class UppercaseRule implements PasswordRule {
    @Override
    public String key() { return "uppercase"; }

    @Override
    public String message() { return "ต้องมีตัวอักษรพิมพ์ใหญ่ (A-Z)"; }

    @Override
    public boolean validate(String password, PasswordValidationContext context) {
        return password.chars().anyMatch(Character::isUpperCase);
    }
}
