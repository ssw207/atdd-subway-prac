package com.subway.subway.line;

import com.subway.subway.common.AcceptanceTest;
import com.subway.subway.line.dto.LineResponse;
import com.subway.subway.line.dto.SectionSaveRequest;
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
        SectionSaveRequest sectionSaveRequest = createSectionSaveRequest(2L, 3L);
        ExtractableResponse<Response> 지하철구간_생성_응답 = 지하철구간_생성_요청(lineResponse.getId(), sectionSaveRequest);

        //then
        응답검증(지하철구간_생성_응답, HttpStatus.CREATED);

        LineResponse 검증할_지하철_노선 = 지하철노선_조회_요청(지하철노선_생성_응답)
                .as(LineResponse.class);

        List<Long> ids = 검증할_지하철_노선.getStations()
                .stream()
                .map(StationResponse::getId)
                .toList();

        assertThat(ids).containsExactly(1L, 2L, 3L);
    }
}
