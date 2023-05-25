package com.subway.line;

import com.subway.line.domian.PathType;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class PathStep {
    public static ExtractableResponse<Response> 지하철_경로조회_요청(Long source, Long target, PathType pathType) {
        return RestAssured.given().log().all()
                .params("source", source, "target", target, "type", pathType.getCode())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/paths")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철_경로조회_요청(String authHeader, Long source, Long target, PathType pathType) {
        return 지하철_경로조회_요청(RestAssured.given().log().all(), authHeader, source, target, pathType);
    }

    public static ExtractableResponse<Response> 지하철_경로조회_요청(RequestSpecification requestSpecification, String authHeader, Long source, Long target, PathType pathType) {
        return requestSpecification
                .params("source", source, "target", target, "type", pathType.getCode())
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/paths")
                .then().log().all()
                .extract();
    }
}
