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
    ALREADY_LOGOUT_ERROR("U0005", HttpStatus.NOT_FOUND, "이미 로그아웃한 사용자입니다."),
    EMAIL_SEND_ERROR("U0006", HttpStatus.NOT_FOUND, "이메일 발송에 실패했습니다."),
    EMAIL_AUTH_ERROR("U0007", HttpStatus.BAD_REQUEST, "유효하지 않은 인증 링크입니다."),
    REQUIRE_EMAIL_AUTH("U0008", HttpStatus.UNAUTHORIZED, "이메일 인증이 필요한 사용자입니다."),
    NON_VALID_USER_PASSWORD("U0009", HttpStatus.NOT_FOUND, "비밀번호가 올바르지 않습니다."),
    DUPLICATE_USER_PASSWORD("U0010", HttpStatus.CONFLICT, "현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다."),
    DUPLICATE_PREVIOUS_NICKNAME("U0011", HttpStatus.CONFLICT, "현재 닉네임과 동일한 닉네임으로 변경할 수 없습니다.")
    ;


    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
