package com.subway.member.domain;

import com.subway.config.properties.JwtProperties;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(new JwtProperties("jwt-token-secret-key", 3600000L));

    @Test
    void 토큰_생성() {
        assertThat(createToken()).isNotNull();
    }

    private String createToken() {
        return jwtTokenProvider.createToken("test", List.of("ROLE_ADMIN"));
    }
}