package com.narae.fliwith.exception.review;

import static com.narae.fliwith.exception.constants.ReviewExceptionList.REVIEW_DELETE_FAIL;

public class ReviewDeleteFailException extends ReviewException{
    public ReviewDeleteFailException(){
        super(REVIEW_DELETE_FAIL.getErrorCode(),
                REVIEW_DELETE_FAIL.getHttpStatus(),
                REVIEW_DELETE_FAIL.getMessage());
    }
}
