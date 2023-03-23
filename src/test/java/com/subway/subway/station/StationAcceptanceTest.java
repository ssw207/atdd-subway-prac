package com.subway.subway.station;

import com.subway.subway.common.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("역 관련 테스트")
public class StationAcceptanceTest extends AcceptanceTest {

    /**
     * when: 역을 추가 하면
     * then: 역이 추가 된다
     */
    @DisplayName("역 추가")
    @Test
    void addStation() {
        //when
        ExtractableResponse<Response> response = RestAssured.given()
                .param("name", "강남역")
                .when().log().all()
                .post("/stations")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
