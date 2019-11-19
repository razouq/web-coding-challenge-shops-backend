package com.bendarsianass.shops.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class AppExceptionsHandler {

    @ExceptionHandler(value = {AccountServiceException.class})
    public ResponseEntity<Object> handleAccountServiceException(AccountServiceException e, WebRequest webRequest) {

        return new ResponseEntity<>(e.getErrorMap(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
