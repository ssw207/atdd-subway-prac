package com.subway.member.dto;

public record GithubTokenRequest(String code) {
    public static GithubTokenRequest of(String code) {
        return new GithubTokenRequest(code);
    }
}
