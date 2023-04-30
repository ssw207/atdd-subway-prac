package com.subway.member.dto;

public record GithubAccessTokenRequest(String code, String clientId, String clientSecret) {
}
