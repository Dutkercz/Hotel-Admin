package hotel_admin.dutkercz.com.github.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> agumentExceptionHandler(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.stream().map(ParseError::new).toList());
    }

    //Mapeia erros que podem ser mais de 1
    record ParseError(String field, String message) {
        ParseError(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
