package com.evolvestage.api.exceptions;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String msg) {
        super(msg);
    }
}
