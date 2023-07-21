package net.lerkasan.capstone.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static java.util.Objects.isNull;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

//    @Autowired
//    private ApplicationContext applicationContext;
    @Autowired
    private UniqueValidatable service;
    private String fieldName;
//    @Override
//    public void initialize(Unique constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }



    @Override
    public void initialize(Unique annotation) {
//        Class<? extends UniqueValidatable> serviceCls = annotation.service();
//        if (applicationContext != null) {
//            service = applicationContext.getBean(serviceCls);
//        }
        fieldName = annotation.field();
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        return isNull(service)  || service.isAvailable(fieldName, fieldValue);
    }

//    @Override
//    public boolean isValid(Object annotatedObject, ConstraintValidatorContext context) {
//        return isNull(applicationContext) || isNull(service) || isNull(annotatedObject) || isValid(annotatedObject);
//    }
//
//    private boolean isValid(Object annotatedObject) {
//        Long id = 0L;
//        Objects.requireNonNull(annotatedObject);
//        String fieldValue = BeanUtils.getProperty(annotatedObject, fieldName);
//        String idStr = BeanUtils.getProperty(annotatedObject, "id");
//        if (idStr != null) {
//            id = Long.valueOf(idStr);
//        }
//        return isNull(fieldValue) || service.isAvailable(fieldName, fieldValue, id);
//    }

}