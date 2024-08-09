package com.narae.fliwith.dto;

import com.narae.fliwith.domain.Disability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserReq {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpReq {
        private String email;
        private String password;
        private String nickname;
        private Disability disability;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KakaoSignUpReq {
        private Long kakaoId;
        private String nickname;
        private Disability disability;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogInReq {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KakaoLogInReq {
        private Long kakaoId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailReq{
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NicknameReq{
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NewPasswordReq{
        private String currentPassword;
        private String newPassword;
    }
}
