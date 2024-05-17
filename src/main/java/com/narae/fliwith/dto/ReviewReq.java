package com.narae.fliwith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReviewReq {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WriteReviewReq{
        private int contentId;
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewImageReq{
        private String imageUrl;
    }
}
