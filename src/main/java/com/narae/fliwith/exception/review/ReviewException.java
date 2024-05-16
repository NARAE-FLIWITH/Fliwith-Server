package com.narae.fliwith.exception.review;

import com.narae.fliwith.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class ReviewException extends ApplicationException{
    protected ReviewException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
