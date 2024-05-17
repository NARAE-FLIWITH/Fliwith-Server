package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.DUPLICATE_USER_NICKNAME;

public class DuplicateUserNicknameException extends UserException{
    public DuplicateUserNicknameException(){
        super(DUPLICATE_USER_NICKNAME.getErrorCode(),
                DUPLICATE_USER_NICKNAME.getHttpStatus(),
                DUPLICATE_USER_NICKNAME.getMessage());
    }
}
