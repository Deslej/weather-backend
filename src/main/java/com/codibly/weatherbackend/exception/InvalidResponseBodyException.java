package com.codibly.weatherbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class InvalidResponseBodyException extends RuntimeException{
    public InvalidResponseBodyException(String message) {
        super(message);
    }
}
