package com.sistema.permissoes.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sistema.permissoes.services.exceptions.BadRequestException;
import com.sistema.permissoes.services.exceptions.NotFoundException;
import com.sistema.permissoes.services.exceptions.RestErrorMessage;
import com.sistema.permissoes.services.exceptions.userexceptions.CreateUserException;
import com.sistema.permissoes.services.exceptions.userexceptions.LoginDisableException;
import com.sistema.permissoes.services.exceptions.userexceptions.PasswordNotMatchException;
import com.sistema.permissoes.utils.FormatList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleSecurityException(RuntimeException ex) {

        ProblemDetail errorDetail = null;

        if (ex instanceof BadCredentialsException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Autenticação falhada!");
        }

        if (ex instanceof AccessDeniedException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("messageError", "Usuário não autorizado!");

            log.error(ex.getMessage(), ex);
        }

        if (ex instanceof JWTVerificationException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Usuário não autorizado!");
        }

        return errorDetail;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {

        String mensagem = "";

        if (ex.getResourcePath().equals("api/caixas/getCaixaFiltrada")) {
            mensagem = "É necessário informar o número, no caminho da url na requisição.";
        }


        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("message", mensagem);
//        responseBody.put("path", ex.getResourcePath());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {

        String mensagem = "É necessário informar o paramêtro na url, no momento da requisição.";


        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("message", mensagem);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateUserException.class)
    private ResponseEntity<List<String>> createUserErroHandler(CreateUserException createUserException) {

        RestErrorMessage response = new RestErrorMessage(HttpStatus.BAD_REQUEST, createUserException.getMessage());

        log.error("", createUserException);

        var errorsList = FormatList.deleteBrackets(response.getMessage());

        return ResponseEntity.status(response.getStatus()).body(errorsList);

    }

    @ExceptionHandler(PasswordNotMatchException.class)
    private ResponseEntity<String> passwordNotMatchErroHandler(PasswordNotMatchException passwordNotMatchException) {

        RestErrorMessage response = new RestErrorMessage(HttpStatus.BAD_REQUEST, passwordNotMatchException.getMessage());

        log.error("", passwordNotMatchException);

        return ResponseEntity.status(response.getStatus()).body(response.getMessage());

    }

    @ExceptionHandler(LoginDisableException.class)
    private ResponseEntity<String> loginDisableErroHandler(LoginDisableException loginDisableException) {

        RestErrorMessage response = new RestErrorMessage(HttpStatus.BAD_REQUEST, loginDisableException.getMessage());

        log.error("", loginDisableException);

        return ResponseEntity.status(response.getStatus()).body(response.getMessage());

    }


    @ExceptionHandler(UnauthorizedException.class)
    private ResponseEntity<String> unauthorizedErroHandler(UnauthorizedException unauthorizedException) {

        RestErrorMessage response = new RestErrorMessage(HttpStatus.UNAUTHORIZED, unauthorizedException.getMessage());

        log.error("", unauthorizedException);

        return ResponseEntity.status(response.getStatus()).body(response.getMessage());

    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<Map<String, String>> notFoundErroHandler(NotFoundException notFoundException) {

        Map<String, String> errors = new HashMap<>();
        errors.put("mensagem", notFoundException.getMessage());

        log.error("", notFoundException);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);

    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<String> badRequestErroHandler(BadRequestException badRequestException) {

        RestErrorMessage response = new RestErrorMessage(HttpStatus.BAD_REQUEST, badRequestException.getMessage());

        log.error("", badRequestException);

        return ResponseEntity.status(response.getStatus()).body(response.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
