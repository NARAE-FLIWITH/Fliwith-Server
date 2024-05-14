package com.narae.fliwith.config.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenUtil tokenUtil;

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenUtil);
        //UsernamePasswordAuthenticationFilter 앞에 필터로 JwtFilter 추가
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
