package com.subway.member;

import com.subway.member.dto.MemberSaveRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class MemberStep {
    public static ExtractableResponse<Response> 회원가입_요청(MemberSaveRequest memberSaveRequest) {
        return RestAssured.given().log().all()
                .body(memberSaveRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/members")
                .then().log().all()
                .extract();
    }
}