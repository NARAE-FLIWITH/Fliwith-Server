package com.narae.fliwith.exception.user;


import static com.narae.fliwith.exception.constants.UserExceptionList.DUPLICATE_PREVIOUS_NICKNAME;

public class DuplicatePreviousNicknameException extends UserException{
    public DuplicatePreviousNicknameException(){
        super(DUPLICATE_PREVIOUS_NICKNAME.getErrorCode(),
                DUPLICATE_PREVIOUS_NICKNAME.getHttpStatus(),
                DUPLICATE_PREVIOUS_NICKNAME.getMessage());
    }
}
