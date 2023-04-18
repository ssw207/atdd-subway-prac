package com.subway.config.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtPropertiesTest {

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void name2() {
        assertThat(jwtProperties.getExpireLength()).isNotZero();
        assertThat(jwtProperties.getSecretKey()).isNotNull();
    }
}