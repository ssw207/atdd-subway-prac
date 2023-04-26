package com.subway.member.domain;

import com.subway.config.properties.JwtProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    public static final String EMAIL = "admin@email.com";
    public static final Role ROLE = Role.ADMIN;
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(new JwtProperties("jwt-token-secret-key", 3600000L));

    @Test
    void 토큰_생성() {
        assertThat(createToken()).isNotNull();
    }

    @Test
    void 토큰_검증() {
        String token = createToken();
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void 토큰_파싱() {
        String token = createToken();
        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo(EMAIL);
        assertThat(jwtTokenProvider.getRole(token).equals(ROLE));
    }

    private String createToken() {
        return jwtTokenProvider.createToken(EMAIL, ROLE);
    }
}