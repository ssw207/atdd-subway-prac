package com.subway.subway.station;

import com.subway.subway.common.AcceptanceTest;
import com.subway.subway.station.service.StationSaveRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("역 관련 테스트")
public class StationAcceptanceTest extends AcceptanceTest {

    /**
     * when: 역을 추가 하면
     * then: 역이 추가 된다
     */
    @DisplayName("역 추가")
    @Test
    void test1() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new StationSaveRequest("강남역"))
                .when().log().all()
                .post("/stations")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
