package ku.cs.project_SE.service.validation.rules;

import ku.cs.project_SE.service.validation.PasswordRule;
import ku.cs.project_SE.service.validation.PasswordValidationContext;

public class NoFirstNameRule implements PasswordRule {
    @Override
    public String key() { return "no-name"; }

    @Override
    public String message() { return "ต้องไม่มีชื่อจริง"; }

    @Override
    public boolean validate(String password, PasswordValidationContext context) {
        if (context.firstName() == null) return true;
        return !password.toLowerCase().contains(context.firstName().toLowerCase());
    }
}
