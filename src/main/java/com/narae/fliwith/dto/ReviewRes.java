package com.narae.fliwith.dto;

import com.narae.fliwith.domain.Disability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class ReviewRes {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewDetailRes {
        private Disability disability;
        private String nickname;
        private LocalDateTime createdAt;
        private Long likes;
        private String spotName;
        private String content;
        private List<String> images;
        private boolean isMine;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewItem {
        private String image;
        private String nickname;
        private Disability disability;
        private Long likes;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewItemRes{
        List<ReviewItem> reviews;
        int pageNo;
        int lastPageNo;

    }
}
