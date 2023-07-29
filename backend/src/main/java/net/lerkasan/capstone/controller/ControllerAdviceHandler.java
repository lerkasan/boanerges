package net.lerkasan.capstone.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.exception.NotFoundException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@ControllerAdvice
public class ControllerAdviceHandler {

    private static final String ERROR_MESSAGE_PROPERTY = "message";
    public static final String MALFORMED_JSON = "Received malformed JSON. ";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "IllegalArgumentException: ";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION = "Constraint Violation exception occurred. ";
    public static final String RUNTIME_EXCEPTION = "RuntimeException: ";
    public static final String NOT_FOUND_EXCEPTION = "NotFoundException: ";
    public static final String RECEIVED_MALFORMED_URL_METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = "Received malformed URL - MethodArgumentTypeMismatchException: ";
    public static final String RECEIVED_MALFORMED_URL = "Received malformed URL.";
    public static final String JPA_OBJECT_RETRIEVAL_FAILURE_EXCEPTION = "JpaObjectRetrievalFailureException: ";
    public static final String NET_LERKASAN_CAPSTONE_MODEL = "net.lerkasan.capstone.model.";
    public static final String DATA_INTEGRITY_VIOLATION_EXCEPTION = "DataIntegrityViolationException: ";
    public static final String ILLEGAL_ID_OF_RELATED_OBJECT = "Illegal id of related object.";
    public static final String INVALID_DATA_ACCESS_API_USAGE_EXCEPTION = "InvalidDataAccessApiUsageException: ";
    public static final String IOEXCEPTION = "IOException: ";

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
        log.error(MALFORMED_JSON + e.getMessage());;
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, MALFORMED_JSON);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleIllegalArgumentException(final Exception e) {
        log.error(ILLEGAL_ARGUMENT_EXCEPTION + e.getMessage());
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
        log.error(CONSTRAINT_VIOLATION_EXCEPTION + errorMessage);
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, errorMessage);
    }

    @ExceptionHandler(NestedRuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleRuntimeException(final Exception e) {
        log.error(RUNTIME_EXCEPTION + e.getMessage());
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public Map<String, String> handleNotFoundException(final Exception e) {
        log.error(NOT_FOUND_EXCEPTION + e.getMessage());
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMethodArgumentTypeMismatchException(final Exception e) {
        log.error(RECEIVED_MALFORMED_URL_METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION + e.getMessage());
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, RECEIVED_MALFORMED_URL);
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleJpaObjectRetrievalFailureException(final JpaObjectRetrievalFailureException e) {
        log.error(JPA_OBJECT_RETRIEVAL_FAILURE_EXCEPTION + e.getMessage());
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getCause().getMessage().replace(NET_LERKASAN_CAPSTONE_MODEL,""));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.error(DATA_INTEGRITY_VIOLATION_EXCEPTION + e.getMessage());
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, ILLEGAL_ID_OF_RELATED_OBJECT);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleInvalidDataAccessApiUsageException(final InvalidDataAccessApiUsageException e) {
        log.error(INVALID_DATA_ACCESS_API_USAGE_EXCEPTION + e.getMessage());
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getCause().getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleIOException(final Exception e) {
        log.error(IOEXCEPTION + e.getMessage());
        return Collections.singletonMap(ERROR_MESSAGE_PROPERTY, e.getCause().getMessage());
    }
}