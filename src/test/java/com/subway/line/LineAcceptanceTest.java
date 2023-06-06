package com.subway.line;

import com.subway.common.AcceptanceTest;
import com.subway.line.dto.LineResponse;
import com.subway.line.dto.LineSaveRequest;
import com.subway.line.dto.LineUpdateRequest;
import com.subway.station.StationStep;
import com.subway.station.dto.StationResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.List;

import static com.subway.common.CommonStep.응답검증;
import static com.subway.line.LineFixture.createLineSaveRequest;
import static com.subway.line.LineFixture.createLineUpdateRequest;
import static org.assertj.core.api.Assertions.assertThat;

class LineAcceptanceTest extends AcceptanceTest {

    private Long 역1;
    private Long 역2;
    private Long 역3;
    private Long 역4;

    @BeforeEach
    void setUp() {
        super.init();

        역1 = StationStep.지하철역_생성_요청("역1").as(Long.class);
        역2 = StationStep.지하철역_생성_요청("역2").as(Long.class);
        역3 = StationStep.지하철역_생성_요청("역3").as(Long.class);
        역4 = StationStep.지하철역_생성_요청("역4").as(Long.class);
    }

    /**
     * when: 지하철 노선을 생성하면
     * then: 지하철 노선 이름, 비용과 역 정보가 조회 된다.
     */
    @Test
    void 지하철_노선_생성_조회() {
        //when
        LineSaveRequest lineSaveRequest = createLineSaveRequest(역1, 역2, "1호선", 3, 10, 900);

        ExtractableResponse<Response> 지하철노선_생성_응답 = LineStep.지하철노선_생성_요청(lineSaveRequest);

        //then
        응답검증(지하철노선_생성_응답, HttpStatus.CREATED);

        ExtractableResponse<Response> 지하철노선_조회_응답 = LineStep.지하철노선_조회_요청(지하철노선_생성_응답);

        응답검증(지하철노선_조회_응답, HttpStatus.OK);
        LineResponse lineResponse = 지하철노선_조회_응답.as(LineResponse.class);

        assertThat(lineResponse.name()).isEqualTo("1호선");
        assertThat(lineResponse.color()).isEqualTo("bg-red-600");
        assertThat(lineResponse.stations().stream().map(StationResponse::id)).containsExactly(역1, 역2);
        assertThat(lineResponse.fare()).isEqualTo(900);
        assertThat(lineResponse.startTime()).isEqualTo(LocalTime.of(05, 0, 0));
        assertThat(lineResponse.endTime()).isEqualTo(LocalTime.of(23, 0, 0));
        assertThat(lineResponse.term()).isEqualTo(600);
    }

    /**
     * given: 지하철 노선을 2개 생성 하고
     * when: 지하철 노선 목록을 조회 하면
     * then: 지하철 노선이 2개 조회 된다.
     */
    @Test
    void 지하철노선_목록_조회() {
        //given
        LineStep.지하철노선_생성_요청(createLineSaveRequest(역1, 역2, "1호선", 3, 10, 900));
        LineStep.지하철노선_생성_요청(createLineSaveRequest(역3, 역4, "2호선", 3, 10, 900));

        //when
        ExtractableResponse<Response> 지하철노선_목록_조회_응답 = LineStep.지하철노선_목록_조회_요청();

        //then
        응답검증(지하철노선_목록_조회_응답, HttpStatus.OK);

        List<LineResponse> lineResponses = 지하철노선_목록_조회_응답.as(new TypeRef<>() {
        });

        assertThat(lineResponses).hasSize(2);
        assertThat(lineResponses.get(0).name()).isEqualTo("1호선");
        assertThat(lineResponses.get(1).name()).isEqualTo("2호선");
    }

    /**
     * given: 지하철 노선을 생성하고
     * when: 지하철 노선의 이름을 name2로 바꾸면
     * then: 지하철 노선의 이름이 변경되고
     * and: 다시 조회한 지하철 노선의 이름은 name2 가 된다
     */
    @Test
    void 지하철노선_수정() {
        //given
        ExtractableResponse<Response> 지하철노선_생성_응답 = LineStep.지하철노선_생성_요청(createLineSaveRequest(역1, 역2, "1호선", 3, 10, 900));
        LineResponse lineResponse = 지하철노선_생성_응답.as(LineResponse.class);

        //when
        LineUpdateRequest lineUpdateRequest = createLineUpdateRequest(lineResponse.id(), lineResponse.name(), "blue");

        ExtractableResponse<Response> 지하철노선_수정_응답 = LineStep.지하철노선_수정_요청(lineResponse, lineUpdateRequest);

        //then
        응답검증(지하철노선_수정_응답, HttpStatus.OK);
        LineResponse updated = LineStep.지하철노선_조회_요청(지하철노선_생성_응답).as(LineResponse.class);
        assertThat(updated.color()).isEqualTo(lineUpdateRequest.color());
    }

    /**
     * given: 지하철 노선을 생성하고
     * when: 지하철 노선을 삭제하면
     * then: 지하철 노선이 삭제된다
     */
    @Test
    void 지하철노선_삭제() {
        //given
        ExtractableResponse<Response> 지하철노선_생성_응답 = LineStep.지하철노선_생성_요청(createLineSaveRequest(역1, 역2, "1호선", 3, 10, 900));
        LineResponse lineResponse = 지하철노선_생성_응답.as(LineResponse.class);

        //when
        ExtractableResponse<Response> 지하철노선_삭제_응답 = LineStep.지하철노선_삭제_요청(lineResponse.id());

        //then
        응답검증(지하철노선_삭제_응답, HttpStatus.NO_CONTENT);
    }
}
