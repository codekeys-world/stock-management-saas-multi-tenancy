package com.codekeys.saas.exceptions;

public class BusinessException extends RuntimeException{
    private final String message;

    public BusinessException(final String message) {
        super(message);
        this.message = message;
    }

}
