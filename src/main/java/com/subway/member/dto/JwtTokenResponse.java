package com.subway.member.dto;

public record JwtTokenResponse(String accessToken) {
    public static JwtTokenResponse of(String accessToken) {
        return new JwtTokenResponse(accessToken);
    }
}
