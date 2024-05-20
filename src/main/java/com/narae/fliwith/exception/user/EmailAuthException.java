package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.EMAIL_AUTH_ERROR;

public class EmailAuthException extends UserException{
    public EmailAuthException(){
        super(EMAIL_AUTH_ERROR.getErrorCode(),
                EMAIL_AUTH_ERROR.getHttpStatus(),
                EMAIL_AUTH_ERROR.getMessage());
    }

}
