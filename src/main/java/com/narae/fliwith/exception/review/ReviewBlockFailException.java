package com.narae.fliwith.exception.review;

import static com.narae.fliwith.exception.constants.ReviewExceptionList.REVIEW_BLOCK_FAIL;

public class ReviewBlockFailException extends ReviewException{
    public ReviewBlockFailException(){
        super(REVIEW_BLOCK_FAIL.getErrorCode(),
                REVIEW_BLOCK_FAIL.getHttpStatus(),
                REVIEW_BLOCK_FAIL.getMessage());
    }
}
