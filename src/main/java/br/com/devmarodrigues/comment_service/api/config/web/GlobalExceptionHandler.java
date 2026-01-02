package br.com.devmarodrigues.comment_service.api.config.web;

import br.com.devmarodrigues.comment_service.api.config.web.customExceptions.ModerateServiceClientBadGatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(ModerateServiceClientBadGatewayException.class)
    public ResponseEntity<Object> handleValidationExceptions(ModerateServiceClientBadGatewayException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Forbiddend words: ", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler({
        SocketTimeoutException.class,
        ConnectException.class,
        ClosedChannelException.class
    })
    public ProblemDetail handle(IOException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);
        problemDetail.setTitle("Gateway timeou");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;
    }
}
