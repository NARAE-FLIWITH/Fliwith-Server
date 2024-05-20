package com.narae.fliwith.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionList {
    DUPLICATE_USER_EMAIL("U0001", HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    LOGIN_FAIL("U0002", HttpStatus.NOT_FOUND, "로그인에 실패했습니다."),
    DUPLICATE_USER_NICKNAME("U0003", HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    NOT_FOUND_USER_ERROR("U0004", HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    ALREADY_LOGOUT_ERROR("U0005", HttpStatus.NOT_FOUND, "이미 로그아웃한 사용자입니다.")
    ;


    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
