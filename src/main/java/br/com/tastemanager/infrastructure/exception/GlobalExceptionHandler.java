package br.com.tastemanager.infrastructure.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "The request body contains invalid or unexpected fields.");
        problemDetail.setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.1"));
        problemDetail.setTitle("Invalid Request Body");
        problemDetail.setInstance(URI.create(request.getDescription(false).substring(4)));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
