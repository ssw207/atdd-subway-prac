package com.subway.member.step;

import com.subway.member.dto.GithubTokenRequest;
import com.subway.member.dto.JwtTokenRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AuthStep {
    public static ExtractableResponse<Response> JWT_토큰_생성요청(JwtTokenRequest request) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .post("/login/token")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 깃허브_토큰_생성요청(GithubTokenRequest tokenRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/github")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }
}
