package com.subway.member;

import com.subway.common.AcceptanceTest;
import com.subway.member.dto.AuthMember;
import com.subway.member.dto.MemberSaveRequest;
import com.subway.member.dto.TokenResponse;
import com.subway.member.fixture.MemberFixture;
import com.subway.member.step.MemberStep;
import com.subway.util.DataLoader;
import com.subway.util.GithubTestUserResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.subway.common.CommonStep.응답검증;
import static com.subway.member.fixture.AuthFixture.createGithubTokenRequestFixture;
import static com.subway.member.fixture.AuthFixture.createJwtTokenRequest;
import static com.subway.member.step.AuthStep.JWT_토큰_생성요청;
import static com.subway.member.step.AuthStep.깃허브_토큰_생성요청;
import static com.subway.member.step.MemberStep.회원가입_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        super.init();
    }

    /**
     * when: 회원가입 요청을 하면
     * then: 회원가입이 된다
     */
    @Test
    void 회원가입() {
        //given
        MemberSaveRequest memberSaveRequest = MemberFixture.createMemberFixture();

        //when
        ExtractableResponse<Response> 회원가입_요청_응답 = 회원가입_요청(memberSaveRequest);

        //when
        응답검증(회원가입_요청_응답, HttpStatus.CREATED);
        assertThat(회원가입_요청_응답.as(Long.class)).isNotNull();
    }

    /**
     * given: 회원가입후 jwt로그인을 하고
     * when: 내 정보 조회 요청을 하면
     * then: 내 정보 조회가 된다
     */
    @Test
    void 내_정보_조회_jwt() {
        String accessToken = JWT_토큰_생성요청(createJwtTokenRequest()).as(TokenResponse.class).accessToken();
        String authHeader = "Bearer " + accessToken;
        //when
        ExtractableResponse<Response> response = MemberStep.내_정보_조회_요청(authHeader);

        //then
        응답검증(response, HttpStatus.OK);
        AuthMember authMember = response.as(AuthMember.class);
        assertThat(authMember.email()).isEqualTo(DataLoader.EMAIL_ADMIN);
        assertThat(authMember.id()).isNotNull();
        assertThat(authMember.age()).isEqualTo(20);
    }

    /**
     * given: 회원가입후 깃허브 로그인을 하고
     * when: 내 정보 조회 요청을 하면
     * then: 내 정보 조회가 된다
     */
    @Test
    void 내_정보_조회_깃허브() {
        String accessToken = 깃허브_토큰_생성요청(createGithubTokenRequestFixture()).as(TokenResponse.class).accessToken();
        String authHeader = "Authorization " + accessToken;
        //when
        ExtractableResponse<Response> response = MemberStep.내_정보_조회_요청(authHeader);

        //then
        응답검증(response, HttpStatus.OK);
        AuthMember authMember = response.as(AuthMember.class);
        assertThat(authMember.email()).isEqualTo(GithubTestUserResponse.사용자1.getEmail());
        assertThat(authMember.id()).isNotNull();
        assertThat(authMember.age()).isEqualTo(0);
    }

}
