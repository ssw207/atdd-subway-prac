package com.subway.member.dto;

public record JwtTokenRequest(String email, String password) {

    public static JwtTokenRequest of(String email, String password) {
        return new JwtTokenRequest(email, password);
    }
}
