package com.subway.member.fixture;

import com.subway.member.dto.GithubTokenRequest;
import com.subway.member.dto.JwtTokenRequest;
import com.subway.util.DataLoader;
import com.subway.util.GithubTestUserResponse;

public class AuthFixture {
    public static JwtTokenRequest createJwtTokenRequest() {
        return JwtTokenRequest.of(DataLoader.EMAIL_ADMIN, DataLoader.PASSWORD);
    }

    public static GithubTokenRequest createGithubTokenRequestFixture() {
        return GithubTokenRequest.of(GithubTestUserResponse.사용자1.getCode());
    }
}
