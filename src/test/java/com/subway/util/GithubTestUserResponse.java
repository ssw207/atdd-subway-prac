package com.subway.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum GithubTestUserResponse {
    사용자1("1", "access_token_1", "email1@email.com"),
    사용자2("2", "access_token_2", "email2@email.com"),
    사용자3("3", "access_token_3", "email3@email.com"),
    사용자4("4", "access_token_4", "email4@email.com");

    private final String code;
    private final String accessToken;
    private final String email;

    public static GithubTestUserResponse findByCode(String code) {
        return findByPredicate(it -> Objects.equals(it.code, code));
    }

    public static GithubTestUserResponse findByToken(String accessToken) {
        return findByPredicate(it -> Objects.equals(it.accessToken, accessToken));
    }

    private static GithubTestUserResponse findByPredicate(Predicate<GithubTestUserResponse> githubTestUserResponsePredicate) {
        return Arrays.stream(values())
                .filter(githubTestUserResponsePredicate)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}

