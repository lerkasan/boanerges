package net.lerkasan.capstone.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<Password, char[]> {

    private static final char MIN_ALLOWED_CHAR_SPACE = 32;
    private static final char MAX_ALLOWED_CHAR_TILDE = 126;

    @Override
    public void initialize(Password annotation) {
    }

    @Override
    public boolean isValid(char[] param, ConstraintValidatorContext context) {
        if (param == null) {
            return false;
        }
        for (char c : param) {
            if ((c < MIN_ALLOWED_CHAR_SPACE) || (c > MAX_ALLOWED_CHAR_TILDE)) {
                return false;
            }
        }
        return true;
    }
}