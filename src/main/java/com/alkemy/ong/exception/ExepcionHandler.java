package com.alkemy.ong.exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExepcionHandler {
    @Autowired
    private MessageSource messageSource;
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<MessagesInfo> handleValidationError(HttpServletRequest request, ConstraintViolationException exception) {
        Map<String, String> transformedError = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            transformedError.put(fieldName.substring(fieldName.lastIndexOf('.') + 1), violation.getMessage());
        }
        MessagesInfo errorInfo = new MessagesInfo(transformedError, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<MessagesInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        Map<String, String> transformedError = new HashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach(f -> transformedError.put(f.getField(), f.getDefaultMessage()));
        MessagesInfo errorInfo = new MessagesInfo(transformedError, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<MessageInfo> metodArgumentEypeExcept(HttpServletRequest request, MethodArgumentTypeMismatchException exception) {
        String message = exception.getMessage();
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<MessageInfo> notFoundExcept(HttpServletRequest request, NotFoundException exception) {
        String message = exception.getMessage();
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<MessageInfo> badException(HttpServletRequest request, BadRequestException exception) {
        String message = exception.getMessage();
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<MessageInfo> numberFormatException(HttpServletRequest request) {
        String message = messageSource.getMessage
                ("message.error.id.not.number", null, Locale.ENGLISH);
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<MessageInfo> messageNotReadableException(HttpServletRequest request) {
        String message = messageSource.getMessage
                ("message.error.unexpected.character", null, Locale.ENGLISH);
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<MessageInfo> illegalArgExcept(HttpServletRequest request, IllegalArgumentException ex) {
        String message = ex.getMessage();
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NotFoundList.class})
    public ResponseEntity<MessageInfo> notFoundList(HttpServletRequest request, NotFoundList exception) {
        String message = exception.getMessage();
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.OK.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }
}
