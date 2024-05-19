package com.narae.fliwith.config.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReissueTokenRes {
    private String accessToken;
    private String refreshToken;

    public static ReissueTokenRes from(TokenRes tokenRes) {
        return ReissueTokenRes.builder()
                .accessToken(tokenRes.getAccessToken())
                .refreshToken(tokenRes.getRefreshToken())
                .build();
    }


}
