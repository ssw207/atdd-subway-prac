package com.subway.line;

import com.subway.line.domian.PathType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class PathStep {
    public static ExtractableResponse<Response> 지하철_경로조회_요청(Long source, Long target, PathType pathType) {
        return given().log().all()
                .params("source", source, "target", target, "type", pathType.getCode())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/paths")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철_경로조회_요청(Long source, Long target, PathType pathType, String startTime) {
        return 지하철_경로조회_요청(given().log().all(), "", source, target, pathType, startTime);
    }

    public static ExtractableResponse<Response> 지하철_경로조회_요청(String authHeader, Long source, Long target, PathType pathType) {
        return 지하철_경로조회_요청(given().log().all(), authHeader, source, target, pathType, "00:00:00");
    }

    public static ExtractableResponse<Response> 지하철_경로조회_요청(RequestSpecification requestSpecification, String authHeader, Long source, Long target, PathType pathType, String startTime) {

        requestSpecification.params("source", source, "target", target, "type", pathType.getCode(), "startTime", startTime);

        if (StringUtils.isNotBlank(authHeader)) {
            requestSpecification.header(HttpHeaders.AUTHORIZATION, authHeader);
        }

        return requestSpecification.contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/paths")
                .then().log().all()
                .extract();
    }
}
