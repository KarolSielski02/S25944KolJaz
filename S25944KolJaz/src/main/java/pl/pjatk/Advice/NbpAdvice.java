package pl.pjatk.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NbpAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> NullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Exception occurred: " + e.getLocalizedMessage());
    }
}
