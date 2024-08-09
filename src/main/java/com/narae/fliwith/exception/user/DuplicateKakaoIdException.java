package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.DUPLICATE_KAKAO_ID;

public class DuplicateKakaoIdException extends UserException {
    public DuplicateKakaoIdException() {
        super(DUPLICATE_KAKAO_ID.getErrorCode(),
                DUPLICATE_KAKAO_ID.getHttpStatus(),
                DUPLICATE_KAKAO_ID.getMessage());
    }
}
