package com.narae.fliwith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ImageRes {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PresignedUrlRes{
        private String presignedUrl;
        private String imageUrl;

    }
}
