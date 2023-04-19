package com.subway.member.domain;

import com.subway.config.properties.JwtProperties;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProviderTest {

    private final JwtProvider jwtProvider = new JwtProvider(new JwtProperties("key", 10L));

    @Test
    void 토큰_생성() {
        assertThat(createToken()).isNotNull();
    }
    
    private String createToken() {
        return jwtProvider.createToken("test", List.of("ROLE_ADMIN"));
    }
}