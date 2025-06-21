package com.codibly.weatherbackend.exception;

import lombok.Getter;


@Getter
public class ExternalApiClientException extends RuntimeException{

    private final int statusCode;

    public ExternalApiClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
