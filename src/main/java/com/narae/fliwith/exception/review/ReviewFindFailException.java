package com.narae.fliwith.exception.review;

import static com.narae.fliwith.exception.constants.ReviewExceptionList.REVIEW_FIND_FAIL;

public class ReviewFindFailException extends ReviewException{
    public ReviewFindFailException(){
        super(REVIEW_FIND_FAIL.getErrorCode(),
                REVIEW_FIND_FAIL.getHttpStatus(),
                REVIEW_FIND_FAIL.getMessage());
    }
}
