package com.sparkplug.application.presentation;

import com.sparkplug.common.exception.ApplicationException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ApplicationException.class})
    public ProblemDetail handleApplicationException(ApplicationException exception) {

        var statusCode = HttpStatusCode.valueOf(exception.getStatusCode());
        var message = exception.getMessage();

        return ProblemDetail.forStatusAndDetail(statusCode, message);
    }

    @ExceptionHandler({
            AuthenticationException.class,
            SignatureException.class,
            AuthorizationDeniedException.class
    })
    public ProblemDetail handleAuthenticationException(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ProblemDetail handleRuntimeException(RuntimeException exception) {

        var statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        var message = exception.getMessage();

        return ProblemDetail.forStatusAndDetail(statusCode, message);
    }
}
