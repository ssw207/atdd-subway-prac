package com.subway.member.domain;

import com.subway.member.service.TokenService;

public class AuthUserFindCommandFactory {

    private static final String TOKEN_PREFIX = "Bearer ";

    public static AuthUserFindCommand createComand(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            throw new IllegalArgumentException("인증 헤더 정보가 없습니다.");
        }

        throw new UnsupportedOperationException(); // TODO 구현 필요
    }

    public Object find(TokenService tokenService) {
        throw new UnsupportedOperationException(); // TODO 구현 필요
    }

    private String parseToken(String authorizationHeader) {
        String replace = authorizationHeader.replace(TOKEN_PREFIX, "");
        throw new UnsupportedOperationException(); // TODO 구현 필요
    }

}
