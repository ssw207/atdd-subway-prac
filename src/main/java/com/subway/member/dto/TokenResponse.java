package com.subway.member.dto;

public record TokenResponse(String accessToken) {
    public static TokenResponse of(String accessToken) {
        return new TokenResponse(accessToken);
    }
}
