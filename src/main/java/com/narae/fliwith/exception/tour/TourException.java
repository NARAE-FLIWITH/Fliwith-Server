package com.narae.fliwith.exception.tour;

import com.narae.fliwith.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class TourException extends ApplicationException {
    protected TourException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
