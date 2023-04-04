package com.subway.subway.line;

import com.subway.subway.common.AcceptanceTest;
import com.subway.subway.line.dto.LineResponse;
import com.subway.subway.station.dto.StationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.subway.subway.common.CommonStep.응답검증;
import static com.subway.subway.line.LineStep.지하철노선_생성_요청;
import static com.subway.subway.line.LineStep.지하철노선_조회_요청;
import static com.subway.subway.line.SectionStep.지하철구간_생성_요청;
import static com.subway.subway.line.SectionsFixture.createSectionSaveRequest;
import static com.subway.subway.station.StationStep.지하철역_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class SectionAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        지하철역_생성_요청("역1");
        지하철역_생성_요청("역2");
        지하철역_생성_요청("역3");
        지하철역_생성_요청("역4");
    }

    /**
     * given: 노선을 생성하고
     * when: 구간을 추가하면
     * then: 구간이 추가되고
     * and: 노선을 다시 조회하면 추가한 구간의 역이 조회된다
     */
    @Test
    void 지하철구간_추가() {
        //given
        ExtractableResponse<Response> 지하철노선_생성_응답 = 지하철노선_생성_요청(LineFixture.createLineSaveRequest(1L, 2L, "노선1"));
        LineResponse lineResponse = 지하철노선_생성_응답.as(LineResponse.class);

        //when
        ExtractableResponse<Response> 지하철구간_생성_응답 = 지하철구간_생성_요청(lineResponse.getId(), createSectionSaveRequest(2L, 3L));

        //then
        응답검증(지하철구간_생성_응답, HttpStatus.CREATED);

        LineResponse 검증할_지하철_노선 = 지하철노선_조회_요청(지하철노선_생성_응답)
                .as(LineResponse.class);

        List<Long> ids = 지하철_역_아이디(검증할_지하철_노선.getStations());
        assertThat(ids).containsExactly(1L, 2L, 3L);
    }

    /**
     * given: 지하철 노선을 등록하고
     * when: 지하철 구간을 삭제하면
     * then: 삭제에 실패한다
     */
    @Test
    void 지하철노선의_구간이_하나면_삭제불가() {

    }

    /**
     * given: 지하철 노선을 등록하고
     * when: 지하철에 없는 역을 삭제하면
     * then: 삭제에 실패한다
     */
    @Test
    void 지하철노선에_없는_역을_삭제하면_삭제에_실패한다() {
    }

    /**
     * given: 지하철 노선과 구간을 등록하고
     * when: 중간역을 제거하고
     * and : 노선을 다시 조회하면
     * then: 중간역이 삭제되고 거리는 두 구간의 합이 된다
     */
    @Test
    void 지하철구간_삭제() {
        //given
        ExtractableResponse<Response> 지하철노선_생성_응답 = 지하철노선_생성_요청(LineFixture.createLineSaveRequest(1L, 2L, "노선1"));
        LineResponse lineResponse = 지하철노선_생성_응답.as(LineResponse.class);
        지하철구간_생성_요청(lineResponse.getId(), createSectionSaveRequest(2L, 3L));

        //when
        ExtractableResponse<Response> 지하철구간_삭제응답 = SectionStep.지하철_구간_삭제_요청(lineResponse);

        //then
        응답검증(지하철구간_삭제응답, HttpStatus.OK);
    }

    private List<Long> 지하철_역_아이디(List<StationResponse> stations) {
        return stations
                .stream()
                .map(StationResponse::getId)
                .toList();
    }
}
