package com.fakesmtp.api.exception;

import com.fakesmtp.api.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for the application.
 * @author Gilberto Vazquez
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles general exceptions.
     * @param ex the exception to handle
     * @param request the HTTP request
     * @return a response with the error details
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException ex,
                                                                HttpServletRequest request) {
        log.error("General exception at {} | {}", request.getRequestURI(), ex.getMessage());
        HttpStatus status = ex.getStatus();

        return ResponseEntity
                .status(status)
                .body(generateErrorResponse(ex, request, status));
    }

    /**
     * Handles all other exceptions.
     * @param ex the exception to handle
     * @param request the HTTP request
     * @return a response with the error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex,
                                                         HttpServletRequest request) {
        log.error("Unexpected exception  {} | {}", request.getRequestURI(), ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(generateErrorResponse(ex, request, status));
    }

    /**
     * Generates an error response.
     * @param ex the exception to handle
     * @param request the HTTP request
     * @return an error response
     */
    private ErrorResponse generateErrorResponse(Exception ex,
                                                HttpServletRequest request,
                                                HttpStatus status) {
        String message = ex.getMessage();
        String path = request.getRequestURI();
        Instant timestamp = Instant.now();

        return new ErrorResponse(
                status.value(),
                status.name(),
                message,
                path,
                timestamp,
                List.of());
    }

    /**
     * Handles method argument not valid exceptions.
     * @param ex the exception to handle
     * @param request the HTTP request
     * @return a response with the error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ErrorResponse.Detail> details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> {
                    if (err instanceof FieldError fieldError) {
                        return new ErrorResponse.Detail(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    return new ErrorResponse.Detail(err.getObjectName(), err.getDefaultMessage());
                })
                .toList();


        String message = "Validation failed";

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.name(),
                message,
                request.getRequestURI(),
                Instant.now(),
                details);

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                          HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String name = ex.getName();
        Class<?> requiredTypeClass = ex.getRequiredType();
        String requiredType = requiredTypeClass == null ? "type" : requiredTypeClass.getSimpleName();

        String message = "Parameter '" + name + "' must be a valid " + requiredType;
        if (ex.getValue() != null) {
            message += " (received: " + ex.getValue() + ")";
        }

        List<ErrorResponse.Detail> details = List.of(new ErrorResponse.Detail(name, message));

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.name(),
                message,
                request.getRequestURI(),
                Instant.now(),
                details);

        return ResponseEntity.status(status).body(error);
    }

    /**
     * Handles constraint violation exceptions.
     * @param ex the exception to handle
     * @param request the HTTP request
     * @return a response with the error details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
                                                                   HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ErrorResponse.Detail> details = ex.getConstraintViolations()
                .stream()
                .map(v -> new ErrorResponse.Detail(v.getPropertyPath().toString(), v.getMessage()))
                .toList();

        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));

        if (message.isBlank()) {
            message = "Validation failed";
        }

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.name(),
                message,
                request.getRequestURI(),
                Instant.now(),
                details);

        return ResponseEntity.status(status).body(error);
    }

    /**
     * Handles http message not readable exceptions.
     * @param ex the exception to handle
     * @param request the HTTP request
     * @return a response with the error details
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                      HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = "The provided request body contains invalid JSON or unreadable content";
        String fieldPath = null;

        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException mappingException) {
            List<JsonMappingException.Reference> path = mappingException.getPath();
            if (path != null && !path.isEmpty()) {
                fieldPath = jsonPathToString(path);
                message = "The provided value for field '" + fieldPath + "' is not in a valid format";
            }
            if (mappingException instanceof InvalidFormatException invalidFormat) {
                log.error("Invalid format for field {} expected {}", fieldPath, invalidFormat.getTargetType());
            }
        }

        List<ErrorResponse.Detail> details = fieldPath == null
                ? List.of()
                : List.of(new ErrorResponse.Detail(fieldPath, message));

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.name(),
                message,
                request.getRequestURI(),
                Instant.now(),
                details);

        return ResponseEntity.status(status).body(error);
    }

    /**
     * Converts a JSON path to a string.
     * @param path the JSON path
     * @return the string representation of the path
     */
    private String jsonPathToString(List<JsonMappingException.Reference> path) {
        return path.stream()
                .map(ref -> {
                    if (ref.getFieldName() != null) {
                        return ref.getFieldName();
                    }
                    if (ref.getIndex() >= 0) {
                        return "[" + ref.getIndex() + "]";
                    }
                    return "?";
                })
                .collect(Collectors.joining("."))
                .replace(".[", "[");
    }
}
