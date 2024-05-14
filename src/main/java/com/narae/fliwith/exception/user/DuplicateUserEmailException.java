package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.DUPLICATE_USER_EMAIL;

public class DuplicateUserEmailException extends UserException{
    public DuplicateUserEmailException(){
        super(DUPLICATE_USER_EMAIL.getErrorCode(),
                DUPLICATE_USER_EMAIL.getHttpStatus(),
                DUPLICATE_USER_EMAIL.getMessage());
    }
}
