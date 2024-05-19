package com.narae.fliwith.exception.tour;

import static com.narae.fliwith.exception.constants.TourExceptionList.NOT_FOUND_AI_TOUR_ERROR;

public class NotFoundAiTourException extends TourException{
    public NotFoundAiTourException(){
        super(NOT_FOUND_AI_TOUR_ERROR.getErrorCode(),
                NOT_FOUND_AI_TOUR_ERROR.getHttpStatus(),
                NOT_FOUND_AI_TOUR_ERROR.getMessage());
    }
}
