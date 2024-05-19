package com.narae.fliwith.config.security.util;


import com.narae.fliwith.config.security.dto.CustomUser;
import com.narae.fliwith.config.security.dto.TokenRes;
import com.narae.fliwith.domain.Token;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.exception.security.ExpiredTokenException;
import com.narae.fliwith.exception.security.InvalidTokenException;
import com.narae.fliwith.exception.user.NotFoundUserException;
import com.narae.fliwith.repository.TokenRepository;
import com.narae.fliwith.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletRequest;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenUtil implements InitializingBean {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public TokenUtil(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Value("${security.authorities-key}")
    private String authoritiesKey;
    @Value("${security.bearer-type}")
    private String bearerType;
    @Value("${security.access-token-expire-time}")
    private Long accessTokenExpireTime;
    @Value("${security.refresh-token-expire-time}")
    private Long refreshTokenExpireTime;
    @Value("${security.reissue-token-expire-time}")
    private Long reissueTokenExpireTime;
    @Value("${security.secret-key}")
    private String secretKey;

    private Key key;


    public TokenRes token(com.narae.fliwith.domain.User user){
        // claim 생성
        Claims claims = getClaims(user);

        Date now = new Date();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now.getTime() + accessTokenExpireTime);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        Date refreshTokenExpiresIn = new Date(now.getTime() + refreshTokenExpireTime);
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + refreshTokenExpireTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(user.getEmail())
                .compact();

        return TokenRes.builder()
                .grantType(bearerType)
                .accessToken(accessToken)
                .refreshTokenExpirationTime(refreshTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(CustomUser user) {

        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, "",
                grantedAuthorities);
    }

    public void makeAuthentication(User user) {
        // Authentication 정보 만들기
        CustomUser customUser = CustomUser.builder()
                .email(user.getEmail())
                .roles(Arrays.asList(user.getRole().toString()))
                .build();

        // ContextHolder 에 Authentication 정보 저장
        Authentication auth = getAuthentication(customUser);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public boolean validateToken(String token, ServletRequest request){
        try {
            Jws<Claims> claims =Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", "4043");
            System.out.println("4043");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "S0004");
            System.out.println("4044");
            throw new ExpiredTokenException();

        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "4045");
            System.out.println("4045");

        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "4046");
            System.out.println("4046");

        }
        return false;
    }

    public TokenRes reissue(String token){
        //token = refreshToken

        //accessToken으로 user를 찾고
        String userEmail = getSubject(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundUserException::new);

        //찾은 user로 refreshToken을 가져오고
        Token preToken = tokenRepository.findByUser(user).orElseThrow(InvalidTokenException::new);

        tokenRepository.delete(preToken);
        TokenRes tokenRes = token(user);
        Token newToken = Token.builder()
                .user(user)
                .refreshToken(tokenRes.getRefreshToken())
                .build();
        tokenRepository.save(newToken);

        return tokenRes;

    }

    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getClaims(com.narae.fliwith.domain.User user) {
        // claim 에 email 정보 추가
        Claims claims = Jwts.claims().setSubject(user.getEmail()).build();
        return claims;
    }
}
