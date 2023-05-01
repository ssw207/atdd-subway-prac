package com.subway.member;

import com.subway.member.dto.TokenResponse;
import com.subway.util.DataLoader;
import com.subway.util.DatabaseCleanup;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.subway.common.CommonStep.응답검증;
import static com.subway.member.fixture.AuthFixture.createGithubTokenRequestFixture;
import static com.subway.member.fixture.AuthFixture.createJwtTokenRequest;
import static com.subway.member.step.AuthStep.JWT_토큰_생성요청;
import static com.subway.member.step.AuthStep.깃허브_토큰_생성요청;
import static org.assertj.core.api.Assertions.assertThat;

// 테스트에서 접근해야 하므로 지정된 포트를 사용한다. application.properties 옵션 참조
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginAcceptanceTest {

    /**
     * givne: 회원 가입을 하고
     * when: 아이디와 비밀번호로 토큰생성을 요청하면
     * then: 토큰이 생성된다
     */

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DataLoader dataLoader;

    @BeforeEach
    protected void init() {
        databaseCleanup.execute();
        dataLoader.loadData();
    }

    @Test
    void jwt_토큰_생성() {
        ExtractableResponse<Response> response = JWT_토큰_생성요청(createJwtTokenRequest());

        응답검증(response, HttpStatus.OK);
        assertThat(response.as(TokenResponse.class).accessToken()).isNotNull();
    }

    /**
     * when: code로 깃허브 인증요청을 하면
     * then: 토큰이 생성된다
     */
    @Test
    void 깃허브_인증() {
        //when
        ExtractableResponse<Response> response = 깃허브_토큰_생성요청(createGithubTokenRequestFixture());

        //then
        assertThat(response.jsonPath().getString("accessToken")).isNotNull(); // not null로 체크해도 괜찮을까?
    }
}
