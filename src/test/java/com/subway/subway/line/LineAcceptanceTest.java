package com.subway.subway.line;

import com.subway.subway.common.AcceptanceTest;
import com.subway.subway.station.StationStep;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.subway.subway.common.CommonStep.응답검증;
import static org.assertj.core.api.Assertions.assertThat;

class LineAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        StationStep.지하철역_생성_요청("역1");
        StationStep.지하철역_생성_요청("역2");
    }

    /**
     * when: 지하철 노선을 생성하면
     * then: 지하철 노선 이름, 비용과 역 정보가 조회 된다.
     */
    @Test
    void 지하철_노선_생성_조회() {
        //when
        ExtractableResponse<Response> 지하철노선_생성_응답 = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new LineSaveRequest("1호선", 0L, 1L, 100))
                .post("/lines")
                .then().log().all()
                .extract();

        //then
        응답검증(지하철노선_생성_응답, HttpStatus.CREATED);

        long id = 지하철노선_생성_응답.jsonPath().getLong("id");
        ExtractableResponse<Response> 지하철노선_조회_응답 = RestAssured.given().log().all()
                .pathParam("id", id)
                .get("/lines/{id}")
                .then().log().all()
                .extract();

        응답검증(지하철노선_조회_응답, HttpStatus.OK);
        assertThat(지하철노선_조회_응답.jsonPath().getString("name")).isEqualTo("1호선");
        assertThat(지하철노선_조회_응답.jsonPath().getList("stations.id")).containsExactly(0L, 1L);
        assertThat(지하철노선_조회_응답.jsonPath().getInt("fare")).isEqualTo(100);
    }
}