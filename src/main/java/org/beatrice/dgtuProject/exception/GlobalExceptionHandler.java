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
                .body(new ErrorResponse("404 NOT FOUND", exception.getMessage()));
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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("409 CONFLICT", exception.getMessage()));
    }

    @ExceptionHandler(MissingTokenException.class)
    public ResponseEntity<?> handleMissingToken(MissingTokenException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("403 UNAUTHORIZED", exception.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFound(TaskNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("404 NOT FOUND", exception.getMessage()));
    }

    @ExceptionHandler(NoAccessEexception.class)
    public ResponseEntity<?> handleNoAccess(NoAccessEexception exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("403 FORBIDDEN", exception.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("403 FORBIDDEN", exception.getMessage()));
    }
}

