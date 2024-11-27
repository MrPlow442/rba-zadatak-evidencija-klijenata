package hr.mlovrekovic.evidencijaklijenata.rest;

import hr.mlovrekovic.evidencijaklijenata.rest.model.ErrorResponse;
import hr.mlovrekovic.evidencijaklijenata.service.exception.AlreadyExistsException;
import hr.mlovrekovic.evidencijaklijenata.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerExceptionHandler  extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_NOT_FOUND_MESSAGE = "Entity not found!";
    private static final String DEFAULT_ALREADY_EXISTS_MESSAGE = "Entity already exists!";
    private static final String DEFAULT_ILLEGAL_ARGUMENT_MESSAGE = "Illegal argument!";

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

    private <T extends RuntimeException> String getMessageOrDefault(T ex, String defaultMessage) {
        return ex.getMessage() != null ? ex.getMessage() : defaultMessage;
    }
}
