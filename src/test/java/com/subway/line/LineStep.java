package com.subway.line;

import com.subway.line.dto.LineResponse;
import com.subway.line.dto.LineSaveRequest;
import com.subway.line.dto.LineUpdateRequest;
import com.subway.station.dto.StationResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class LineStep {

    public static ExtractableResponse<Response> 지하철노선_조회_요청(long id) {
        return RestAssured.given().log().all()
                .pathParam("id", id)
                .get("/lines/{id}")
                .then().log().all()
                .extract();
    }

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

    public static ExtractableResponse<Response> 지하철노선_수정_요청(LineResponse lineResponse, LineUpdateRequest lineUpdateRequest) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(lineUpdateRequest)
                .pathParam("id", lineResponse.getId())
                .put("/lines/{id}")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철노선_삭제_요청(Long id) {
        return given().log().all()
                .pathParam("id", id)
                .delete("/lines/{id}")
                .then().log().all()
                .extract();
    }

    public static void 노선의_지하철역_검증(Long 노선_id, Long... 검증학역) {
        LineResponse 검증할_지하철_노선 = 지하철노선_조회_요청(노선_id).as(LineResponse.class);
        지하철_역_검증(검증할_지하철_노선.getStations(), 검증학역);
    }

    public static void 지하철_역_검증(List<StationResponse> stations, Long... 검증할역들) {
        List<Long> ids = 지하철_역_아이디(stations);
        assertThat(ids).containsExactly(검증할역들);
    }

    public static List<Long> 지하철_역_아이디(List<StationResponse> stations) {
        return stations
                .stream()
                .map(StationResponse::getId)
                .toList();
    }

}
