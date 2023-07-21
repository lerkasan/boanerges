package net.lerkasan.capstone.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
//@Documented
public @interface Unique {
    String message() default "";
    String field();

//    Class<? extends UniqueValidatable> service();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
//    @Documented
    @interface List
    {
        Unique[] value();
    }
}