package br.com.tastemanager.shared.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetail.setType(java.net.URI.create("https://example.com/validation-error"));
        problemDetail.setTitle("Validation Error");
        problemDetail.setDetail("Erro de validação nos campos enviados.");
        problemDetail.setInstance(java.net.URI.create("/api/v1/user"));
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        problemDetail.setProperty("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetail.setType(java.net.URI.create("https://example.com/illegal-argument"));
        problemDetail.setTitle("Illegal Argument");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setInstance(java.net.URI.create("/api/v1/user"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Não foi possível excluir o usuário pois ele está associado a outros registros (por exemplo, restaurantes).");
        problem.setTitle("Data Integrity Violation");
        problem.setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7807#section-3.1"));
        problem.setInstance(URI.create("/api/v1/user/delete"));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneralException(Exception ex) {
        // Log temporário para debug - remover depois
        System.out.println("=== DEBUG: Tipo de exceção recebida: " + ex.getClass().getName());
        System.out.println("=== DEBUG: Mensagem: " + ex.getMessage());

        Throwable cause = ex;
        while (cause != null) {
            System.out.println("=== DEBUG: Verificando causa: " + cause.getClass().getName());
            if (cause instanceof DataIntegrityViolationException) {
                System.out.println("=== DEBUG: Encontrou DataIntegrityViolationException na cadeia!");
                ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                        HttpStatus.CONFLICT,
                        "Não foi possível excluir o usuário pois ele está associado a outros registros (por exemplo, restaurantes)."
                );
                problem.setTitle("Data Integrity Violation");
                problem.setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7807#section-3.1"));
                problem.setInstance(URI.create("/api/v1/user/delete"));
                return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
            }
            cause = cause.getCause();
        }

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
        problem.setTitle("Internal Server Error");
        problem.setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7807#section-3.1"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(UserNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("User Not Found");
        problem.setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7807#section-3.1"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(UserTypeNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUserTypeNotFoundException(UserTypeNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("User Type Not Found");
        problem.setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7807#section-3.1"));
        problem.setProperty("options", ex.getOptions());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(ItemMenuNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleItemMenuNotFoundException(ItemMenuNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Item Menu Not Found");
        problem.setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7807#section-3.1"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Restaurant Not Found");
        problem.setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7807#section-3.1"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

}
