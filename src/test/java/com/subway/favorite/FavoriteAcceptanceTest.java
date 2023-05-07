package com.subway.favorite;

import com.subway.common.AcceptanceTest;
import com.subway.line.LineStep;
import com.subway.line.SectionFixture;
import com.subway.line.SectionStep;
import com.subway.line.dto.LineResponse;
import com.subway.member.dto.TokenResponse;
import com.subway.station.StationStep;
import com.subway.station.dto.StationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.subway.common.CommonStep.응답검증;
import static com.subway.favorite.FavoriteFixture.createFavoriteFixture;
import static com.subway.favorite.FavoriteStep.즐겨찾기_생성_요청;
import static com.subway.favorite.FavoriteStep.즐겨찾기_조회_요청;
import static com.subway.line.LineFixture.createLineSaveRequest;
import static com.subway.member.fixture.AuthFixture.createJwtTokenRequest;
import static com.subway.member.step.AuthStep.JWT_토큰_생성요청;
import static org.assertj.core.api.Assertions.assertThat;

public class FavoriteAcceptanceTest extends AcceptanceTest {

    private Long 역1;
    private Long 역2;
    private Long 역3;

    @BeforeEach
    void setUp() {
        super.init();

        역1 = StationStep.지하철역_생성_요청("역1").as(Long.class);
        역2 = StationStep.지하철역_생성_요청("역2").as(Long.class);
        역3 = StationStep.지하철역_생성_요청("역3").as(Long.class);
        Long 노선 = LineStep.지하철노선_생성_요청(createLineSaveRequest(역1, 역2, "1호선", 3)).as(LineResponse.class).getId();
        SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역2, 역3, 10));
    }

    /**
     * given: jwt 로그인후
     * when: 출발지와 목적지로 즐겨찾기 등록을 요청하면
     * then: 즐겨 찾기가 생성된다
     */
    @Test
    void 즐겨찾기_생성() {
        //given
        ExtractableResponse<Response> 토큰생성요청 = JWT_토큰_생성요청(createJwtTokenRequest());
        String authHeader = "Bearer " + 토큰생성요청.as(TokenResponse.class).accessToken();

        //when
        ExtractableResponse<Response> 즐겨찾기생성_응답 = 즐겨찾기_생성_요청(createFavoriteFixture(역1, 역3), authHeader);

        //then
        응답검증(즐겨찾기생성_응답, HttpStatus.CREATED);
        assertThat(즐겨찾기생성_응답.header(HttpHeaders.LOCATION)).startsWith("/favorites/");
    }

    /**
     * given: jwt로그인후 즐겨찾기를 등록한뒤
     * when: 즐겨찾기를 조회하면
     * then: 즐겨찾기가 조회된다
     */
    @Test
    void 즐겨찾기_조회() {
        //given
        ExtractableResponse<Response> 토큰생성요청 = JWT_토큰_생성요청(createJwtTokenRequest());

        String authHeader = "Bearer " + 토큰생성요청.as(TokenResponse.class).accessToken();

        즐겨찾기_생성_요청(createFavoriteFixture(역1, 역3), authHeader);

        //when
        ExtractableResponse<Response> 즐겨찾기응답 = 즐겨찾기_조회_요청(authHeader);

        //then
        FavoriteResponse favoriteResponse = 즐겨찾기응답.as(FavoriteResponse.class);

        응답검증(즐겨찾기응답, HttpStatus.OK);
        assertThat(favoriteResponse.id()).isNotZero();
        역검증(favoriteResponse.source());
        역검증(favoriteResponse.target());
    }

    private void 역검증(StationResponse station) {
        assertThat(station.id()).isNotZero();
        assertThat(station.name()).isNotNull();
    }
}

