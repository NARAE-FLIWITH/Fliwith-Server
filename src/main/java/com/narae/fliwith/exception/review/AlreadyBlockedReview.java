package com.narae.fliwith.exception.review;


import static com.narae.fliwith.exception.constants.ReviewExceptionList.ALREADY_BLOCKED_REVIEW;

public class AlreadyBlockedReview extends ReviewException{
    public AlreadyBlockedReview(){
        super(ALREADY_BLOCKED_REVIEW.getErrorCode(),
                ALREADY_BLOCKED_REVIEW.getHttpStatus(),
                ALREADY_BLOCKED_REVIEW.getMessage());
    }
}
