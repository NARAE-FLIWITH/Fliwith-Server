package com.narae.fliwith.exception.review;

import static com.narae.fliwith.exception.constants.ReviewExceptionList.REVIEW_LIST_ERROR;

public class ReviewListException extends ReviewException{
    public ReviewListException(){
        super(REVIEW_LIST_ERROR.getErrorCode(),
                REVIEW_LIST_ERROR.getHttpStatus(),
                REVIEW_LIST_ERROR.getMessage());
    }
}
