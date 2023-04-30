package com.subway.favorite;

import com.subway.favorite.dto.FavoriteRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class FavoriteStep {
    public static ExtractableResponse<Response> 즐겨찾기_생성_요청(FavoriteRequest favoriteRequest) {
        return RestAssured.given().log().all()
                .body(favoriteRequest)
                .when().log().all()
                .post("/favorites")
                .then().log().all()
                .extract();
    }
}
