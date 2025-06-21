package com.codibly.weatherbackend.exception;

import lombok.Getter;

@Getter
public class ExternalApiServerException extends RuntimeException{

    private final int statusCode;

    public ExternalApiServerException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
