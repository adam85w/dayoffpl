package net.adam85w.dayoff;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DayOffExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", HttpStatus.BAD_REQUEST.name());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = { DateTimeParseException.class, MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Map<String, String>> handleDateTimeParseException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", HttpStatus.BAD_REQUEST.name());
        errorResponse.put("message", "Invalid date format. Please use dd-MM-yyyy format.");
        errorResponse.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorResponse.put("message", "The server was unable to complete your request.");
        errorResponse.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}