package com.subway.member.dto;

public record GithubProfileResponse(String email) {

    public static GithubProfileResponse of(String email) {
        return new GithubProfileResponse(email);
    }
}
