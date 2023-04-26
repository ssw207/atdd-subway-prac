package com.subway.member.dto;

public record JwtTokenResponse(String accessToken) {
    public static JwtTokenResponse of(String token) {
        return new JwtTokenResponse(token);
    }
}
