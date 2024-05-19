package com.narae.fliwith.exception.security;

import static com.narae.fliwith.exception.security.constants.SecurityExceptionList.INVALID_TOKEN_ERROR;

public class InvalidTokenException extends SecurityException{
    public InvalidTokenException(){
        super(INVALID_TOKEN_ERROR.getErrorCode(),
                INVALID_TOKEN_ERROR.getHttpStatus(),
                INVALID_TOKEN_ERROR.getMessage());
    }
}
