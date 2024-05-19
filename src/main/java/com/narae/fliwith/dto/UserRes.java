package com.narae.fliwith.dto;

import com.narae.fliwith.domain.Disability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRes {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileRes{
        private Disability disability;
        private String nickname;
    }
}
