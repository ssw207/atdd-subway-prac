package com.subway.member.dto;

public record GithubAccessTokenResponse(String accessToken, String tokenType, String scope, String bearer) {
    
}
