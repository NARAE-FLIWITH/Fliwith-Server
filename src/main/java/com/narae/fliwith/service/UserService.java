package com.narae.fliwith.service;

import com.narae.fliwith.config.security.dto.ReissueTokenRes;
import com.narae.fliwith.config.security.dto.TokenRes;
import com.narae.fliwith.config.security.util.TokenUtil;
import com.narae.fliwith.domain.Role;
import com.narae.fliwith.domain.Token;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.dto.UserReq;
import com.narae.fliwith.dto.UserReq.EmailReq;
import com.narae.fliwith.dto.UserReq.NicknameReq;
import com.narae.fliwith.dto.UserRes.ProfileRes;
import com.narae.fliwith.exception.security.InvalidTokenException;
import com.narae.fliwith.exception.user.DuplicateUserEmailException;
import com.narae.fliwith.exception.user.DuplicateUserNicknameException;
import com.narae.fliwith.exception.user.LogInFailException;
import com.narae.fliwith.repository.TokenRepository;
import com.narae.fliwith.repository.UserRepository;
import jakarta.servlet.ServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public void signUp(UserReq.SignUpReq signUpReq) {
        if(userRepository.existsByEmail(signUpReq.getEmail())){
            throw new DuplicateUserEmailException();
        }

        String encodedPassword = passwordEncoder.encode(signUpReq.getPassword());

        User user = User.builder()
                .email(signUpReq.getEmail())
                .pw(encodedPassword)
                .role(Role.ROLE_USER)
                .nickname(signUpReq.getNickname())
                .disability(signUpReq.getDisability())
                .build();

        userRepository.save(user);

    }

    public TokenRes logIn(UserReq.LogInReq logInReq) {
        User user = userRepository.findByEmail(logInReq.getEmail()).orElseThrow(LogInFailException::new);

        if(passwordEncoder.matches(logInReq.getPassword(), user.getPw())){
            tokenUtil.makeAuthentication(user);
            TokenRes tokenRes = tokenUtil.token(user);

            Token token = Token.builder()
                    .user(user)
                    .refreshToken(tokenRes.getRefreshToken())
                    .build();
            tokenRepository.findByUser(user).ifPresent(t->tokenRepository.delete(t));
            tokenRepository.save(token);
            return tokenRes;
        } else{
            throw new LogInFailException();
        }

    }
    //TODO: 로그아웃시 tokenRepository delete



    public void emailCheck(EmailReq emailReq) {
        if(userRepository.existsByEmail(emailReq.getEmail())){
            throw new DuplicateUserEmailException();
        }

    }

    public void nicknameCheck(NicknameReq nicknameReq) {
        if(userRepository.existsByNickname(nicknameReq.getNickname())){
            throw new DuplicateUserNicknameException();
        }

    }

    public ProfileRes getProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(LogInFailException::new);
        return ProfileRes.builder()
                .disability(user.getDisability())
                .nickname(user.getNickname())
                .build();
    }

    public TokenRes reissue(String token, ServletRequest request) {
        // refresh 토큰이 유효한지 확인
        if (token != null && tokenUtil.validateToken(token, request)) {

            // 토큰 새로 받아오기
            return tokenUtil.reissue(token);
        }
        throw new InvalidTokenException();
    }




}
