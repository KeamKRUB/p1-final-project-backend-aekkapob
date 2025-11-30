package ku.cs.project_SE.service.validation.rules;

import ku.cs.project_SE.service.validation.PasswordRule;
import ku.cs.project_SE.service.validation.PasswordValidationContext;

public class MinLengthRule implements PasswordRule {
    @Override
    public String key() { return "length"; }

    @Override
    public String message() { return "ต้องมีอย่างน้อย 8 ตัวอักษร"; }

    @Override
    public boolean validate(String password, PasswordValidationContext context) {
        return password != null && password.length() >= 8;
    }
}
