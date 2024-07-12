package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.NON_VALID_USER_PASSWORD;

public class NonValidUserPasswordException extends UserException{
    public NonValidUserPasswordException(){
        super(NON_VALID_USER_PASSWORD.getErrorCode(),
                NON_VALID_USER_PASSWORD.getHttpStatus(),
                NON_VALID_USER_PASSWORD.getMessage());
    }
}