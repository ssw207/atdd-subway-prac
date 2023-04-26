package com.subway.member;

import com.subway.common.AcceptanceTest;
import com.subway.member.dto.JwtTokenResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.subway.common.CommonStep.응답검증;
import static com.subway.member.AuthFixture.createJwtTokenRequest;
import static com.subway.member.AuthStep.JWT_토큰_생성요청;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginAcceptanceTest extends AcceptanceTest {
    
    /**
     * givne: 회원 가입을 하고
     * when: 아이디와 비밀번호로 토큰생성을 요청하면
     * then: 토큰이 생성된다
     */
    @Test
    void jwt_토큰_생성() {
        ExtractableResponse<Response> response = JWT_토큰_생성요청(createJwtTokenRequest());

        응답검증(response, HttpStatus.OK);
        assertThat(response.as(JwtTokenResponse.class).accessToken()).isNotNull();
    }

}
