package com.narae.fliwith.exception.security;

import com.narae.fliwith.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class SecurityException  extends ApplicationException {
    protected SecurityException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}