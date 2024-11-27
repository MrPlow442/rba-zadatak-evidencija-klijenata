package hr.mlovrekovic.evidencijaklijenata.rest;

import hr.mlovrekovic.evidencijaklijenata.rest.model.ErrorResponse;
import hr.mlovrekovic.evidencijaklijenata.service.exception.AlreadyExistsException;
import hr.mlovrekovic.evidencijaklijenata.service.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_NOT_FOUND_MESSAGE = "Entity not found!";
    private static final String DEFAULT_ALREADY_EXISTS_MESSAGE = "Entity already exists!";
    private static final String DEFAULT_ILLEGAL_ARGUMENT_MESSAGE = "Illegal argument!";
    private static final String DEFAULT_SERVER_ERROR = "Server error!";

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(getMessageOrDefault(ex, DEFAULT_NOT_FOUND_MESSAGE)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ AlreadyExistsException.class })
    public ResponseEntity<ErrorResponse> handleAlreadyExists(AlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(getMessageOrDefault(ex, DEFAULT_ALREADY_EXISTS_MESSAGE)),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(getMessageOrDefault(ex, DEFAULT_ILLEGAL_ARGUMENT_MESSAGE)),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> String.format("%s: %s", f.getField(), f.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        var errorMessage = ex.getConstraintViolations().stream()
                .map(f -> String.format("%s: %s", f.getPropertyPath().toString(), f.getMessage()))
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse> handle(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(DEFAULT_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private <T extends RuntimeException> String getMessageOrDefault(T ex, String defaultMessage) {
        return ex.getMessage() != null ? ex.getMessage() : defaultMessage;
    }
}
