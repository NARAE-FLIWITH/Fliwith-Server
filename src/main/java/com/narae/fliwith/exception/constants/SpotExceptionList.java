package com.narae.fliwith.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SpotExceptionList {
    SPOT_FIND_FAIL("SP0001", HttpStatus.NOT_FOUND, "관광지를 찾는데 실패했습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
