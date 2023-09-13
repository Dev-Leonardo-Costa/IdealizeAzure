package com.idealize.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    public UnsupportedMathOperationException(String message) {
        super(message);
    }
}
