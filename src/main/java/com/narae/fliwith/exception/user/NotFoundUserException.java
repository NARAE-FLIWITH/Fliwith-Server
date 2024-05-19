package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.NOT_FOUND_USER_ERROR;

public class NotFoundUserException extends UserException{
    public NotFoundUserException(){
        super(NOT_FOUND_USER_ERROR.getErrorCode(),
                NOT_FOUND_USER_ERROR.getHttpStatus(),
                NOT_FOUND_USER_ERROR.getMessage());
    }
}
