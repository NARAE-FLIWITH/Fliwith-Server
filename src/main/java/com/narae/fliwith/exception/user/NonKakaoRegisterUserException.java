package com.narae.fliwith.exception.user;


import static com.narae.fliwith.exception.constants.UserExceptionList.NON_KAKAO_REGISTER_USER;

public class NonKakaoRegisterUserException extends UserException {
    public NonKakaoRegisterUserException(){
        super(NON_KAKAO_REGISTER_USER.getErrorCode(),
                NON_KAKAO_REGISTER_USER.getHttpStatus(),
                NON_KAKAO_REGISTER_USER.getMessage());
    }
}
