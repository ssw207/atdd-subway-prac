package com.subway.subway.line;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class PathStep {
    public static ExtractableResponse<Response> 지하철_경로조회_요청(Long source, Long target) {
        return RestAssured.given().log().all()
                .params("source", source, "target", target)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/paths")
                .then().log().all()
                .extract();
    }
}
