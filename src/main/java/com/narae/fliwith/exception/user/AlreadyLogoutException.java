package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.ALREADY_LOGOUT_ERROR;

public class AlreadyLogoutException extends UserException{
    public AlreadyLogoutException(){
        super(ALREADY_LOGOUT_ERROR.getErrorCode(),
                ALREADY_LOGOUT_ERROR.getHttpStatus(),
                ALREADY_LOGOUT_ERROR.getMessage());
    }
}
