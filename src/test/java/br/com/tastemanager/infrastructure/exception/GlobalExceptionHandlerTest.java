package br.com.tastemanager.infrastructure.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void handleHttpMessageNotReadable_ShouldReturnProblemDetail() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/api/v1/user/create");

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("bad json", (Throwable) null);

        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

        assertEquals(400, response.getStatusCode().value());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertNotNull(body);
        assertEquals("Invalid Request Body", body.getTitle());
        assertEquals("https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.1", body.getType().toString());
        assertEquals("/api/v1/user/create", body.getInstance().toString());
    }
}

