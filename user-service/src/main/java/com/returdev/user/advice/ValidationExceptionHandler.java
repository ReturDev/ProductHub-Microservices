package com.returdev.user.advice;


import com.returdev.user.exceptions.InvalidEnumValueException;
import com.returdev.user.util.managers.MessageManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling various types of exceptions in the application.
 * <p>
 * This class provides custom handling for validation errors, type mismatches, and other
 * application-specific exceptions, returning structured responses with detailed error messages.
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler {

    private final MessageManager messageManager;

    /**
     * Handles exceptions when validation on method arguments fails.
     *
     * @param ex the {@link MethodArgumentNotValidException} containing validation errors
     * @return a {@link ProblemDetail} with a 400 BAD REQUEST status and validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessages.put(error.getField(), error.getDefaultMessage())
        );
        return getErrorsProblemDetail(errorMessages);
    }

    /**
     * Handles exceptions when constraint violations occur on method parameters.
     *
     * @param ex the {@link ConstraintViolationException} containing constraint violations
     * @return a {@link ProblemDetail} with a 400 BAD REQUEST status and violation details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldPath = violation.getPropertyPath().toString();
            errorMessages.put(fieldPath.substring(fieldPath.lastIndexOf('.') + 1), violation.getMessage());
        });
        return getErrorsProblemDetail(errorMessages);
    }

    /**
     * Handles exceptions when an invalid value is passed for an enumeration type.
     *
     * @param ex the {@link InvalidEnumValueException} containing the invalid and valid values
     * @return a {@link ProblemDetail} with a 400 BAD REQUEST status and an appropriate error message
     */
    @ExceptionHandler(InvalidEnumValueException.class)
    public ProblemDetail handleInvalidEnumValueException(InvalidEnumValueException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageManager.getMessage(ex.getMessage(), new String[]{ex.getInvalidEnumValue(), ex.getValidValues()})
        );
    }

    /**
     * Handles exceptions when an illegal argument is passed to a method.
     *
     * @param ex the {@link IllegalArgumentException} thrown
     * @return a {@link ProblemDetail} with a 400 BAD REQUEST status and the exception message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles exceptions when there is a type mismatch for method arguments.
     *
     * @param ex the {@link MethodArgumentTypeMismatchException} thrown
     * @return a {@link ProblemDetail} with a 400 BAD REQUEST status and a detailed error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();
        String requiredTypeName = requiredType != null ? requiredType.getSimpleName() : "Unspecified";
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageManager.getMessage("exception.generic.type_mismatch", new String[]{requiredTypeName})
        );
    }

    /**
     * Handles exceptions when an HTTP message is not readable.
     *
     * @param ex the {@link HttpMessageNotReadableException} thrown
     * @return a {@link ProblemDetail} with a 400 BAD REQUEST status and an error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageManager.getMessage("exception.json.request_malformed")
        );
    }

    /**
     * Handles exceptions when an entity is not found.
     *
     * @param ex the {@link EntityNotFoundException} thrown
     * @return a {@link ProblemDetail} with a 404 NOT FOUND status and the exception message
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    /**
     * Constructs a {@link ProblemDetail} object for validation errors.
     *
     * @param errorMessages a map containing field names and their respective validation error messages
     * @return a {@link ProblemDetail} object with a 400 BAD REQUEST status and the error details
     */
    private ProblemDetail getErrorsProblemDetail(Map<String, String> errorMessages) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageManager.getMessage("validation.detail.failed.message")
        );
        problemDetail.setProperty("errors", errorMessages);
        return problemDetail;
    }
}

