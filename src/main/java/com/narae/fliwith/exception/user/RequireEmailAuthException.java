package com.narae.fliwith.exception.user;

import static com.narae.fliwith.exception.constants.UserExceptionList.REQUIRE_EMAIL_AUTH;

public class RequireEmailAuthException extends UserException {
    public RequireEmailAuthException(){
        super(REQUIRE_EMAIL_AUTH.getErrorCode(),
                REQUIRE_EMAIL_AUTH.getHttpStatus(),
                REQUIRE_EMAIL_AUTH.getMessage());
    }
}
