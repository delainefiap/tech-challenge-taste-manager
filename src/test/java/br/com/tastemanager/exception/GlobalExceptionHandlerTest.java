package br.com.tastemanager.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequest() {
        // montar BindingResult com FieldErrors
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("object", "login", "must not be blank");
        FieldError fieldError2 = new FieldError("object", "email", "must be a valid email");
        List<FieldError> fieldErrors = Arrays.asList(fieldError1, fieldError2);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<ProblemDetail> response = handler.handleValidationExceptions(ex);

        assertEquals(400, response.getStatusCode().value());
        ProblemDetail body = response.getBody();
        assertNotNull(body);
        assertEquals("Validation Error", body.getTitle());
        assertEquals("Erro de validação nos campos enviados.", body.getDetail());
        assertTrue(body.getProperties().containsKey("errors"));
        @SuppressWarnings("unchecked")
        var errors = (java.util.Map<String, String>) body.getProperties().get("errors");
        assertEquals(2, errors.size());
        assertEquals("must not be blank", errors.get("login"));
        assertEquals("must be a valid email", errors.get("email"));
    }

    @Test
    void handleIllegalArgumentException_ShouldReturnBadRequest() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ResponseEntity<ProblemDetail> response = handler.handleIllegalArgumentException(exception);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Illegal Argument", response.getBody().getTitle());
        assertEquals("Invalid argument", response.getBody().getDetail());
    }

    @Test
    void handleGeneralException_ShouldReturnInternalServerError() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception exception = new Exception("Unexpected error");
        ResponseEntity<ProblemDetail> response = handler.handleGeneralException(exception);
        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().getTitle());
        assertNotNull(response.getBody().getDetail());
        assertTrue(response.getBody().getDetail().contains("Unexpected error"));
    }

    @Test
    void handleUserNotFoundException_ShouldReturnNotFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        UserNotFoundException exception = new UserNotFoundException("User not found");
        ResponseEntity<ProblemDetail> response = handler.handleUserNotFoundException(exception);
        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("User Not Found", response.getBody().getTitle());
        assertEquals("User not found", response.getBody().getDetail());
    }

    @Test
    void handleUserTypeNotFoundException_ShouldReturnBadRequest() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        UserTypeNotFoundException exception = new UserTypeNotFoundException("User type not found", null);
        ResponseEntity<ProblemDetail> response = handler.handleUserTypeNotFoundException(exception);
        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("User Type Not Found", response.getBody().getTitle());
        assertEquals("User type not found", response.getBody().getDetail());
    }

}