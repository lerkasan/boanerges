package net.lerkasan.capstone.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String PASSWORD_SYMBOLS = "Password can include upper and lower case latin letters, numerals (0-9) and special symbols.";

    String message() default PASSWORD_SYMBOLS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}