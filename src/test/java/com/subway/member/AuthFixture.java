package com.subway.member;

import com.subway.member.dto.JwtTokenRequest;
import com.subway.util.DataLoader;

public class AuthFixture {
    public static JwtTokenRequest createJwtTokenRequest() {
        return JwtTokenRequest.of(DataLoader.EMAIL_ADMIN, DataLoader.PASSWORD);
    }
}
