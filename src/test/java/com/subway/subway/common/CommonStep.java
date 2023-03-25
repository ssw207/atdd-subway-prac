package com.subway.subway.common;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;

public class CommonStep {
    
    public static void 응답검증(ExtractableResponse<Response> response, HttpStatus status) {
        Assertions.assertThat(response.statusCode()).isEqualTo(status.value());
    }
}