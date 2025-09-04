package hotel_admin.dutkercz.com.github.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> agumentExceptionHandler(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.stream().map(ParseError::new).toList());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFound(EntityNotFoundException e, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/clients/find-client";
    }

    @ExceptionHandler(RoomConflictException.class)
    public ResponseEntity<?> roomConflict(RoomConflictException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + ". Volte a página e escolha outro Apartamento" );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolation(ConstraintViolationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    //Mapeia erros que podem ser mais de 1
    record ParseError(String field, String message) {
        ParseError(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
