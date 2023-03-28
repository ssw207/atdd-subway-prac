package com.subway.subway.line;

import com.subway.subway.common.AcceptanceTest;
import com.subway.subway.common.CommonStep;
import com.subway.subway.line.dto.LineResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.subway.subway.station.StationStep.지하철역_생성_요청;

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
     * and: 구간이 추가된다
     */
    @Test
    void 지하철구간_추가() {
        //given
        Long lineId = LineStep.지하철노선_생성_요청(LineFixture.createLineSaveRequest(1L, 2L, "노선1")).as(LineResponse.class).getId();

        //when
        ExtractableResponse<Response> 지하철노선_생성_응답 = SectionStep.지하철노선_생성_요청(lineId, SectionsFixture.createSectionSaveRequest(2L, 3L));

        CommonStep.응답검증(지하철노선_생성_응답, HttpStatus.CREATED);
    }
}
