package com.narae.fliwith.service;

import com.narae.fliwith.config.security.dto.CustomUser;
import com.narae.fliwith.domain.SignupStatus;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.exception.user.LogInFailException;
import com.narae.fliwith.exception.user.NotFoundUserException;
import com.narae.fliwith.exception.user.RequireEmailAuthException;
import com.narae.fliwith.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;

    public User authUser(CustomUser customUser) {
        String email = customUser.getEmail();
        Long kakaoId = customUser.getKakaoId();

        if (email == null && kakaoId == null) {
            throw new LogInFailException();
        }

        User user = (email != null)
                ? userRepository.findByEmail(email).orElseThrow(LogInFailException::new)
                : userRepository.findByKakaoId(kakaoId).orElseThrow(LogInFailException::new);

        if (!user.getSignupStatus().equals(SignupStatus.COMPLETE)) {
            throw new RequireEmailAuthException();
        }
        return user;
    }

    public User authEmailUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(LogInFailException::new);
        if (!user.getSignupStatus().equals(SignupStatus.COMPLETE)) {
            throw new RequireEmailAuthException();
        }
        return user;
    }


    public void checkAuthEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(NotFoundUserException::new);
        if (!user.getSignupStatus().equals(SignupStatus.COMPLETE)) {
            throw new RequireEmailAuthException();
        }
    }
}
