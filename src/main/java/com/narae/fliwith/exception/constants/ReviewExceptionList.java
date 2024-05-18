package com.narae.fliwith.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewExceptionList {
    REVIEW_FIND_FAIL("R0001", HttpStatus.NOT_FOUND, "리뷰를 찾는데 실패했습니다."),
    REVIEW_ACCESS_FAIL("R0002", HttpStatus.FORBIDDEN, "리뷰에 접근할 권한이 없습니다."),
    REVIEW_LIST_ERROR("R0003", HttpStatus.BAD_REQUEST, "리뷰 목록을 불러오지 못했습니다."),
    IMAGE_EXTENSION_ERROR("R0004", HttpStatus.BAD_REQUEST, "유효하지 않은 이미지 확장자 입니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
