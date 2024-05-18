package com.narae.fliwith.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.narae.fliwith.exception.review.ImageExtensionException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ImageReq {

    @AllArgsConstructor
    @Getter
    public enum ImageExtension{
        JPEG("jpeg"),
        JPG("jpg"),
        PNG("png");

        @JsonValue
        private final String uploadExtension;

        @JsonCreator
        public static ImageExtension of(String imageExtension){
            return Arrays.stream(ImageExtension.values())
                    .filter(e->e.uploadExtension.equals(imageExtension))
                    .findAny().orElseThrow(ImageExtensionException::new);
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PresignedUrlReq{
        ImageExtension imageExtension;

    }
}
