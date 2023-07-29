package net.lerkasan.capstone.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.isNull;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

    @Autowired
    private UniqueValidatable service;
    private String fieldName;

    @Override
    public void initialize(Unique annotation) {
        fieldName = annotation.field();
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        return isNull(service)  || service.isAvailable(fieldName, fieldValue);
    }
}