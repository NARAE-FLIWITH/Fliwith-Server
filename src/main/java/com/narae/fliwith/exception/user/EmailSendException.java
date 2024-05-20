package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.EMAIL_SEND_ERROR;

public class EmailSendException extends UserException{
    public EmailSendException(){
        super(EMAIL_SEND_ERROR.getErrorCode(),
                EMAIL_SEND_ERROR.getHttpStatus(),
                EMAIL_SEND_ERROR.getMessage());
    }
}
