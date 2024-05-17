package com.narae.fliwith.exception.spot;

import static com.narae.fliwith.exception.constants.SpotExceptionList.SPOT_FIND_FAIL;

public class SpotFindFailException extends SpotException {
    public SpotFindFailException(){
        super(SPOT_FIND_FAIL.getErrorCode(),
                SPOT_FIND_FAIL.getHttpStatus(),
                SPOT_FIND_FAIL.getMessage());
    }
}
