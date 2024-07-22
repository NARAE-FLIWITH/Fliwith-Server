package com.narae.fliwith.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Column(columnDefinition = "TEXT")
    private String pw;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Disability disability;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private SignupStatus signupStatus;
    private String auth;
    //TODO: 탈퇴상태 추가

    public void completeSignup(){
        signupStatus = SignupStatus.COMPLETE;
    }

    public void changePWd(String encodedPw) {
        pw = encodedPw;
    }

    public void changeNickname(String newNickname) {
        nickname = newNickname;
    }
}
