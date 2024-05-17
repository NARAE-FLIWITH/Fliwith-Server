package com.narae.fliwith.exception.spot;

import com.narae.fliwith.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class SpotException extends ApplicationException {
    protected SpotException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
