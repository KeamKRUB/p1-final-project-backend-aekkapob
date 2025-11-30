package ku.cs.project_SE.service.validation.rules;

import ku.cs.project_SE.service.validation.PasswordRule;
import ku.cs.project_SE.service.validation.PasswordValidationContext;

public class NoEmailUsernameRule implements PasswordRule {
    @Override
    public String key() { return "no-email-username"; }

    @Override
    public String message() { return "ต้องไม่มีชื่ออีเมล"; }

    @Override
    public boolean validate(String password, PasswordValidationContext context) {
        if (context.email() == null) return true;
        String username = context.email().split("@")[0];
        return !password.toLowerCase().contains(username.toLowerCase());
    }
}
