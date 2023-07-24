package net.lerkasan.capstone.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import net.lerkasan.capstone.exception.NotFoundException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerAdviceHandler {

    private static final String ERROR_MESSAGE_PROPERTY = "message";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        final List<ObjectError> objectErrors = e.getBindingResult().getGlobalErrors();
        final String fieldErrorsString = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(joining(" "));
        final String objectErrorsString = objectErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(joining(" "));
        final String errorMessage = String.join(" ", fieldErrorsString, objectErrorsString).trim();
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, errorMessage);
    }

    @ExceptionHandler(value = {JsonParseException.class, JsonMappingException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleJsonParseException(final Exception e) {
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, "Received malformed JSON.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleIllegalArgumentException(final Exception e) {
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleConstraintViolationExceptionException(final ConstraintViolationException e) {
        final String errorMessage = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(joining(" "));
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, errorMessage);
    }

    @ExceptionHandler(NestedRuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleRuntimeException(final Exception e) {
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(NOT_FOUND)
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public Map<String, String> handleNotFoundException(final Exception e) {
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMethodArgumentTypeMismatchException(final Exception e) {
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, "Received malformed URL.");
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleJpaObjectRetrievalFailureException(final JpaObjectRetrievalFailureException e) {
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getCause().getMessage().replace("net.lerkasan.capstone.model.",""));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, "Illegal id of related object.");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleInvalidDataAccessApiUsageException(final InvalidDataAccessApiUsageException e) {
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getCause().getMessage());
    }
}