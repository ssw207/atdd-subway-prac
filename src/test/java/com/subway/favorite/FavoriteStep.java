package com.subway.favorite;

import com.subway.favorite.dto.FavoriteRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class FavoriteStep {
    public static ExtractableResponse<Response> 즐겨찾기_생성_요청(FavoriteRequest favoriteRequest, String authHeader) {

        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(favoriteRequest)
                .when().log().all()
                .post("/favorites")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 즐겨찾기_조회_요청(String authHeader) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .get("/favorites")
                .then().log().all()
                .extract();
    }
}
