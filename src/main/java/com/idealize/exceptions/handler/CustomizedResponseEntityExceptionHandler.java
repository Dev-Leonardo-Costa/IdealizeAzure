package com.idealize.exceptions.handler;

import com.idealize.exceptions.ResponseException;
import com.idealize.exceptions.UnsupportedMathOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseException> handlerAllExceptions(Exception ex, WebRequest request){
        ResponseException exception = new ResponseException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UnsupportedMathOperationException.class)
    public final ResponseEntity<ResponseException> handlerBadRequestExceptions(Exception ex, WebRequest request){
        ResponseException exception = new ResponseException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

}
