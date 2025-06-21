package com.codibly.weatherbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
public class ExternalApiConnectionException extends RuntimeException{

    public ExternalApiConnectionException(String message) {
        super(message);
    }
}
