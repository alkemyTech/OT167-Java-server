package com.alkemy.ong.exception;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExepcionHandler {
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
    public ResponseEntity<MessageInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach(f -> errorMessage.append(f.getField()).append(" ").append(f.getDefaultMessage()).append(" "));
        MessageInfo errorInfo = new MessageInfo(errorMessage.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
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
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
