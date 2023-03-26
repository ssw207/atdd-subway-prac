package com.subway.subway.line;

import com.subway.subway.line.dto.LineSaveRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class LineStep {
    public static ExtractableResponse<Response> 지하철노선_조회_요청(ExtractableResponse<Response> 지하철노선_생성_응답) {
        return RestAssured.given().log().all()
                .get(지하철노선_생성_응답.header(HttpHeaders.LOCATION))
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철노선_생성_요청(LineSaveRequest lineSaveRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(lineSaveRequest)
                .post("/lines")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철노선_목록_조회_요청() {
        return given().log().all()
                .get("/lines")
                .then().log().all()
                .extract();
    }
}
