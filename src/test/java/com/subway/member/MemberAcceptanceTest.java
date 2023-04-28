package com.subway.member;

import com.subway.common.AcceptanceTest;
import com.subway.member.dto.JwtTokenResponse;
import com.subway.member.dto.MemberResponse;
import com.subway.member.dto.MemberSaveRequest;
import com.subway.member.fixture.MemberFixture;
import com.subway.member.step.MemberStep;
import com.subway.util.DataLoader;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.subway.common.CommonStep.응답검증;
import static com.subway.member.fixture.AuthFixture.createJwtTokenRequest;
import static com.subway.member.step.AuthStep.JWT_토큰_생성요청;
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
     * given: 회원가입후 로그인을 하고
     * when: 내 정보 조회 요청을 하면
     * then: 내 정보 조회가 된다
     */
    @Test
    void 내_정보_조회() {
        //given
        String accessToken = JWT_토큰_생성요청(createJwtTokenRequest()).as(JwtTokenResponse.class).accessToken();

        //when
        ExtractableResponse<Response> response = MemberStep.내_정보_조회_요청(accessToken);

        //then
        응답검증(response, HttpStatus.OK);
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertThat(memberResponse.email()).isEqualTo(DataLoader.EMAIL_ADMIN);
    }
}
