package com.subway.member.fixture;

import com.subway.member.dto.GithubTokenRequest;
import com.subway.member.dto.JwtTokenRequest;
import com.subway.util.DataLoader;

public class AuthFixture {
    public static JwtTokenRequest createJwtTokenRequest() {
        return JwtTokenRequest.of(DataLoader.EMAIL_ADMIN, DataLoader.PASSWORD);
    }

    public static GithubTokenRequest createGithubTokenRequestFixture() {
        return GithubTokenRequest.of("832ovnq039hfjn");
    }
}
