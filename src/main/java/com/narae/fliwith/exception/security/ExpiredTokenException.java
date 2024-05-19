package com.narae.fliwith.exception.security;

import static com.narae.fliwith.exception.security.constants.SecurityExceptionList.EXPIRED_TOKEN_ERROR;

public class ExpiredTokenException extends SecurityException{
    public ExpiredTokenException(){
        super(EXPIRED_TOKEN_ERROR.getErrorCode(),
                EXPIRED_TOKEN_ERROR.getHttpStatus(),
                EXPIRED_TOKEN_ERROR.getMessage());
    }

}
