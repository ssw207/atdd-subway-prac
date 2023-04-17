package com.subway.subway.member;

import com.subway.subway.common.AcceptanceTest;
import com.subway.subway.member.dto.MemberSaveRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.subway.subway.common.CommonStep.응답검증;
import static com.subway.subway.member.MemberStep.회원가입_요청;
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
}
