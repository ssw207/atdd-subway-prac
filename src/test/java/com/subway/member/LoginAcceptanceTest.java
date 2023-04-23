package com.subway.member;

import com.subway.common.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.subway.common.CommonStep.응답검증;
import static com.subway.member.AuthFixture.createJwtTokenRequest;
import static com.subway.member.AuthStep.JWT_토큰_생성요청;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginAcceptanceTest extends AcceptanceTest {

    public static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbC5jb20iLCJpYXQiOjE2ODIyNDc4MTksImV4cCI6MTY4MjI1MTQxOSwicm9sZXMiOlsiUk9MRV9BRE1JTiJdfQ.xnU_9I2vZ_4ACbucf4Fmva9Qap5yU8OJljKVboDDbHs";

    /**
     * givne: 회원 가입을 하고
     * when: 아이디와 비밀번호로 토큰생성을 요청하면
     * then: 토큰이 생성된다
     */
    @Test
    void jwt_토큰_생성() {
        ExtractableResponse<Response> response = JWT_토큰_생성요청(createJwtTokenRequest());

        응답검증(response, HttpStatus.OK);
        assertThat(response.as(JwtTokenResponse.class).accessToken()).isEqualTo(JWT_TOKEN);
    }

}
