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
     * then: 역이 추가 되고
     * and: 목록이 조회된다.
     */
    @Test
    void 역_추가() {
        //when
        ExtractableResponse<Response> 지하철역_생성_응답 = 지하철역_생성_요청(new StationSaveRequest("강남역"));

        //then
        응답검증(지하철역_생성_응답, HttpStatus.CREATED);
    }

    private void 응답검증(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isEqualTo(status.value());
    }
    
    private ExtractableResponse<Response> 지하철역_생성_요청(StationSaveRequest req) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().log().all()
                .post("/stations")
                .then().log().all()
                .extract();
    }
}
