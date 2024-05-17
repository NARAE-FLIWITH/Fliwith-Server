package com.narae.fliwith.service;

import com.narae.fliwith.config.security.dto.TokenRes;
import com.narae.fliwith.config.security.util.TokenUtil;
import com.narae.fliwith.domain.Role;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.dto.UserReq;
import com.narae.fliwith.dto.UserReq.EmailReq;
import com.narae.fliwith.dto.UserReq.NicknameReq;
import com.narae.fliwith.exception.user.DuplicateUserEmailException;
import com.narae.fliwith.exception.user.DuplicateUserNicknameException;
import com.narae.fliwith.exception.user.LogInFailException;
import com.narae.fliwith.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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
            return token(logInReq);
        } else{
            throw new LogInFailException();
        }

    }

    private TokenRes token(UserReq.LogInReq user){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenRes tokenRes = tokenUtil.generateTokenRes(authentication);

        // 5. 토큰 발급
        return tokenRes;
    }

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
}
