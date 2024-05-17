package com.narae.fliwith.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewExceptionList {
    REVIEW_FIND_FAIL("R0001", HttpStatus.NOT_FOUND, "리뷰를 찾는데 실패했습니다."),
    REVIEW_DELETE_FAIL("R0002", HttpStatus.FORBIDDEN, "리뷰를 삭제할 권한이 없습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
