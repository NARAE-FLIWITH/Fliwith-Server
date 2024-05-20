package com.narae.fliwith.controller;

import com.narae.fliwith.config.security.dto.CustomUser;
import com.narae.fliwith.config.security.dto.ReissueTokenRes;
import com.narae.fliwith.config.security.dto.TokenRes;
import com.narae.fliwith.dto.UserReq.*;
import com.narae.fliwith.dto.UserRes.ProfileRes;
import com.narae.fliwith.dto.base.BaseRes;
import com.narae.fliwith.service.UserService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup/email")
    public ResponseEntity<BaseRes<Void>> signUp(@RequestBody SignUpReq signUpReq){
        userService.signUp(signUpReq);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "이메일 회원가입에 성공했습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseRes<TokenRes>> signUp(@RequestBody LogInReq logInReq) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "로그인에 성공했습니다.", userService.logIn(logInReq)));
    }

    @PostMapping("/email")
    public ResponseEntity<BaseRes<Void>> emailCheck(@RequestBody EmailReq emailReq) {
        userService.emailCheck(emailReq);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "사용할 수 있는 이메일입니다."));
    }

    @PostMapping("/nickname")
    public ResponseEntity<BaseRes<Void>> nicknameCheck(@RequestBody NicknameReq nicknameReq) {
        userService.nicknameCheck(nicknameReq);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "사용할 수 있는 닉네임입니다."));
    }

    @GetMapping("/profile")
    public ResponseEntity<BaseRes<ProfileRes>> getProfile(@AuthenticationPrincipal CustomUser customUser){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "프로필 조회에 성공했습니다.", userService.getProfile(customUser.getEmail())));
    }

    @GetMapping("/reissue")
    public ResponseEntity<BaseRes<TokenRes>> reissue(@RequestHeader(value = "RefreshToken") String token, ServletRequest request){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "토큰 재발급에 성공했습니다.", userService.reissue(token, request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseRes<Void>> logout(@AuthenticationPrincipal CustomUser customUser) {
        userService.logout(customUser.getEmail());
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "로그아웃에 성공했습니다."));
    }


}
