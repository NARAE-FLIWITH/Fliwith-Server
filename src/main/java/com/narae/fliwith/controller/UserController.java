package com.narae.fliwith.controller;

import com.narae.fliwith.config.security.dto.TokenRes;
import com.narae.fliwith.dto.UserReq.*;
import com.narae.fliwith.dto.base.BaseRes;
import com.narae.fliwith.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseRes<Void>> signUp(@RequestBody SignUpReq signUpReq){
        userService.signUp(signUpReq);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "회원가입에 성공했습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseRes<TokenRes>> signUp(@RequestBody LogInReq logInReq) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "로그인에 성공했습니다.", userService.logIn(logInReq)));
    }
}
