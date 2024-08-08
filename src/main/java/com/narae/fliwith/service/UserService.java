package com.narae.fliwith.service;

import com.narae.fliwith.config.security.dto.CustomUser;
import com.narae.fliwith.config.security.dto.TokenRes;
import com.narae.fliwith.config.security.util.TokenUtil;
import com.narae.fliwith.domain.Role;
import com.narae.fliwith.domain.SignupStatus;
import com.narae.fliwith.domain.Token;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.dto.UserReq;
import com.narae.fliwith.dto.UserReq.EmailReq;
import com.narae.fliwith.dto.UserReq.NewPasswordReq;
import com.narae.fliwith.dto.UserReq.NicknameReq;
import com.narae.fliwith.dto.UserRes.ProfileRes;
import com.narae.fliwith.exception.security.InvalidTokenException;
import com.narae.fliwith.exception.user.AlreadyLogoutException;
import com.narae.fliwith.exception.user.DuplicateKakaoIdException;
import com.narae.fliwith.exception.user.DuplicatePreviousNicknameException;
import com.narae.fliwith.exception.user.DuplicateUserEmailException;
import com.narae.fliwith.exception.user.DuplicateUserNicknameException;
import com.narae.fliwith.exception.user.DuplicateUserPasswordException;
import com.narae.fliwith.exception.user.EmailAuthException;
import com.narae.fliwith.exception.user.LogInFailException;
import com.narae.fliwith.exception.user.NonValidUserPasswordException;
import com.narae.fliwith.repository.TokenRepository;
import com.narae.fliwith.repository.UserRepository;
import jakarta.servlet.ServletRequest;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private final AuthService authService;

    public void signUp(UserReq.SignUpReq signUpReq) {
        if(userRepository.existsByEmail(signUpReq.getEmail())){
            throw new DuplicateUserEmailException();
        }

        nicknameCheck(new NicknameReq(signUpReq.getNickname()));

        String encodedPassword = passwordEncoder.encode(signUpReq.getPassword());

        User user = User.builder()
                .email(signUpReq.getEmail())
                .pw(encodedPassword)
                .role(Role.ROLE_USER)
                .nickname(signUpReq.getNickname())
                .disability(signUpReq.getDisability())
                .signupStatus(SignupStatus.ING)
                .auth(UUID.randomUUID().toString())
                .build();

        user = userRepository.save(user);
        mailService.sendMail(user);

    }

    public void kakaoSignUp(UserReq.KakaoSignUpReq signUpReq) {
        if(userRepository.existsByKakaoId(signUpReq.getKakaoId())){
            throw new DuplicateKakaoIdException();
        }

        nicknameCheck(new NicknameReq(signUpReq.getNickname()));

        User user = User.builder()
                .role(Role.ROLE_USER)
                .nickname(signUpReq.getNickname())
                .disability(signUpReq.getDisability())
                .signupStatus(SignupStatus.COMPLETE)
                .build();

        userRepository.save(user);

    }

    public TokenRes logIn(UserReq.LogInReq logInReq) {
        User user = authService.authEmailUser(logInReq.getEmail());

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

    public ProfileRes getProfile(CustomUser customUser) {
        User user = authService.authUser(customUser);

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

    public void logout(CustomUser customUser) {
        User user = authService.authUser(customUser);
        Token token = tokenRepository.findByUser(user).orElseThrow(AlreadyLogoutException::new);
        tokenRepository.delete(token);

    }

    public void updateSignupStatus(String auth){
        User user = userRepository.findByAuth(auth).orElseThrow(EmailAuthException::new);
        user.completeSignup();
    }

    public void temporaryPassword(String email) {
        User user = authService.authEmailUser(email);
        mailService.tempoaryPassword(user);
    }

    public void changePassword(String email, NewPasswordReq newPasswordReq) {
        User user = authService.authEmailUser(email);
        if(newPasswordReq.getCurrentPassword().equals(newPasswordReq.getNewPassword())){
            throw new DuplicateUserPasswordException();
        }

        if(passwordEncoder.matches(newPasswordReq.getCurrentPassword(), user.getPw())){
            String encodedPassword = passwordEncoder.encode(newPasswordReq.getNewPassword());
            user.changePWd(encodedPassword);
        } else{
            throw new NonValidUserPasswordException();
        }
    }

    public void changeNickname(CustomUser customUser, NicknameReq newNicknameReq){
        User user = authService.authUser(customUser);

        String newNickname = newNicknameReq.getNickname();
        //이전과 동일한 닉네임 불가
        if(user.getNickname().equals(newNickname)){
            throw new DuplicatePreviousNicknameException();
        }

        //중복확인
        if(userRepository.existsByNickname(newNickname)){
            throw new DuplicateUserNicknameException();
        }

        //닉네임 변경
        user.changeNickname(newNickname);
    }

}
