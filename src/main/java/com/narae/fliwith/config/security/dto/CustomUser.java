package com.narae.fliwith.config.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomUser {
    private String email;
    private Long kakaoId;
    private List<String> roles;
}
