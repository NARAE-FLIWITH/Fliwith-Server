package com.narae.fliwith.dto;

import com.narae.fliwith.domain.Spot;
import java.util.List;
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
        private List<String> images;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewImageReq{
        private String imageUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateReviewReq{
        private Spot spot;
        private String content;
        private List<String> images;
    }
}
