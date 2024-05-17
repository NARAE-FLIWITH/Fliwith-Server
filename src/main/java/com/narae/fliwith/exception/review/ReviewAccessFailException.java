package com.narae.fliwith.exception.review;

import static com.narae.fliwith.exception.constants.ReviewExceptionList.REVIEW_ACCESS_FAIL;

public class ReviewAccessFailException extends ReviewException{
    public ReviewAccessFailException(){
        super(REVIEW_ACCESS_FAIL.getErrorCode(),
                REVIEW_ACCESS_FAIL.getHttpStatus(),
                REVIEW_ACCESS_FAIL.getMessage());
    }
}
