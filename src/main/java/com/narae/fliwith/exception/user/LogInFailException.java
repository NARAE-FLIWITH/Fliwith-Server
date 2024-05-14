package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.LOGIN_FAIL;

public class LogInFailException extends UserException {
    public LogInFailException(){
        super(LOGIN_FAIL.getErrorCode(),
                LOGIN_FAIL.getHttpStatus(),
                LOGIN_FAIL.getMessage());
    }
}
