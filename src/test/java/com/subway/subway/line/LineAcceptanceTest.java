package com.subway.subway.line;

import com.subway.subway.common.AcceptanceTest;
import com.subway.subway.line.dto.LineResponse;
import com.subway.subway.line.dto.LineSaveRequest;
import com.subway.subway.station.dto.StationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.subway.subway.common.CommonStep.응답검증;
import static com.subway.subway.line.LineFixture.createLineSaveRequest;
import static com.subway.subway.line.LineStep.지하철노선_생성_요청;
import static com.subway.subway.line.LineStep.지하철노선_조회_요청;
import static com.subway.subway.station.StationStep.지하철역_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

class LineAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        지하철역_생성_요청("역1");
        지하철역_생성_요청("역2");
    }

    /**
     * when: 지하철 노선을 생성하면
     * then: 지하철 노선 이름, 비용과 역 정보가 조회 된다.
     */
    @Test
    void 지하철_노선_생성_조회() {
        //when
        LineSaveRequest lineSaveRequest = createLineSaveRequest();

        ExtractableResponse<Response> 지하철노선_생성_응답 = 지하철노선_생성_요청(lineSaveRequest);

        //then
        응답검증(지하철노선_생성_응답, HttpStatus.CREATED);

        ExtractableResponse<Response> 지하철노선_조회_응답 = 지하철노선_조회_요청(지하철노선_생성_응답);

        응답검증(지하철노선_조회_응답, HttpStatus.OK);
        LineResponse lineResponse = 지하철노선_조회_응답.as(LineResponse.class);

        assertThat(lineResponse.getName()).isEqualTo("1호선");
        assertThat(lineResponse.getStations().stream().map(StationResponse::getId)).containsExactly(1L, 2L);
        assertThat(lineResponse.getFare()).isEqualTo(1);
    }
}