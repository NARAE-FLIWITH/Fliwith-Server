package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.DUPLICATE_USER_PASSWORD;

public class DuplicateUserPasswordException extends UserException{
    public DuplicateUserPasswordException(){
        super(DUPLICATE_USER_PASSWORD.getErrorCode(),
                DUPLICATE_USER_PASSWORD.getHttpStatus(),
                DUPLICATE_USER_PASSWORD.getMessage());
    }
}