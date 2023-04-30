package com.subway.favorite;

import com.subway.favorite.dto.FavoriteRequest;
import com.subway.member.dto.JwtTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class FavoriteStep {
    public static ExtractableResponse<Response> 즐겨찾기_생성_요청(ExtractableResponse<Response> 토큰생성요청, FavoriteRequest favoriteRequest) {
        String accessToken = 토큰생성요청.as(JwtTokenResponse.class).accessToken(); // 즐겨찾기는 항상 로그인 이후에 진행하므로 응답을 그대로 받는다

        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(favoriteRequest)
                .when().log().all()
                .post("/favorites")
                .then().log().all()
                .extract();
    }
}
