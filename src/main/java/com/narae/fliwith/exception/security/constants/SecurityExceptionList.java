package com.narae.fliwith.exception.security.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionList {
    UNKNOWN_ERROR("S0001", HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류가 발생했습니다."),
    ACCESS_DENIED("S0002", HttpStatus.UNAUTHORIZED, "401 접근이 거부되었습니다."),
    ACCESS_DENIED_03("S0003", HttpStatus.UNAUTHORIZED, "403 접근이 거부되었습니다."),
    EXPIRED_TOKEN_ERROR("S0004", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN_ERROR("S0005", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.")
    ;
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
