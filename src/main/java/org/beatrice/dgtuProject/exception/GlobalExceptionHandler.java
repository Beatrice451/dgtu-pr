package org.beatrice.dgtuProject.exception;


import org.beatrice.dgtuProject.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidTaskStatusException.class)
    public ResponseEntity<?> handleInvalidTaskStatus(InvalidTaskStatusException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("400 BAD REQUEST", exception.getMessage()));
    }

    @ExceptionHandler(DeadlinePassedException.class)
    public ResponseEntity<?> handleDeadlinePassed(DeadlinePassedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("400 BAD REQUEST", exception.getMessage()));
    }
}
