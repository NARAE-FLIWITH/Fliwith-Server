package com.narae.fliwith.exception.review;

import static com.narae.fliwith.exception.constants.ReviewExceptionList.IMAGE_EXTENSION_ERROR;

public class ImageExtensionException extends ReviewException{
    public ImageExtensionException(){
        super(IMAGE_EXTENSION_ERROR.getErrorCode(),
                IMAGE_EXTENSION_ERROR.getHttpStatus(),
                IMAGE_EXTENSION_ERROR.getMessage());
    }
}
