package com.narae.fliwith.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TourExceptionList {
    NOT_FOUND_AI_TOUR_ERROR("T0001", HttpStatus.NOT_FOUND, "추천 관광지가 존재하지 않습니다.")
    ;

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
