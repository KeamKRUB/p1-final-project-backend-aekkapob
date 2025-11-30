package ku.cs.project_SE.service.validation;

import ku.cs.project_SE.service.validation.rules.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordValidator {

    private final List<PasswordRule> rules = List.of(
            new MinLengthRule(),
            new LowercaseRule(),
            new UppercaseRule(),
            new NumberRule(),
            new SpecialCharacterRule(),
            new NoFirstNameRule(),
            new NoEmailUsernameRule()
    );

    public String getErrorMessage(String password, PasswordValidationContext context) {
        if (password == null || password.isBlank()) {
            return "กรุณากรอกรหัสผ่าน";
        }

        for (PasswordRule rule : rules) {
            if (!rule.validate(password, context)) {
                return rule.message();
            }
        }
        return null; // ผ่านทั้งหมด
    }

    public boolean isValid(String password, PasswordValidationContext context) {
        return getErrorMessage(password, context) == null;
    }
}
