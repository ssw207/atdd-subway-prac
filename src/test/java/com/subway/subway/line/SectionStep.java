package com.subway.subway.line;

import com.subway.subway.line.dto.SectionSaveRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class SectionStep {
    public static ExtractableResponse<Response> 지하철노선_생성_요청(Long lineId, SectionSaveRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .pathParam("id", lineId)
                .when().log().all()
                .post("/line/{id}/sections")
                .then().log().all()
                .extract();
    }
}
