package com.subway.station;

import com.subway.station.dto.StationSaveRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class StationStep {

    public static ExtractableResponse<Response> 지하철역_생성_요청(String name) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new StationSaveRequest(name))
                .when().log().all()
                .post("/stations")
                .then().log().all()
                .extract();
    }
}