package triple.review.exception;

import jdk.jshell.spi.ExecutionControl.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        ErrorResult errorResult = new ErrorResult("userException", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> illegalExHandler(IllegalArgumentException e) {
        ErrorResult errorResult = new ErrorResult("BAD", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> exception(Exception e) {
        ErrorResult errorResult = new ErrorResult("Exception", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    public class ErrorResult {
        private String code;
        private String message;
    }
}
