package com.narae.fliwith.exception.user;


import static com.narae.fliwith.exception.constants.UserExceptionList.NON_VALID_SIGNUP_USER;

public class NonValidSignupUserException extends UserException{
    public NonValidSignupUserException(){
        super(NON_VALID_SIGNUP_USER.getErrorCode(),
                NON_VALID_SIGNUP_USER.getHttpStatus(),
                NON_VALID_SIGNUP_USER.getMessage());
    }
}
