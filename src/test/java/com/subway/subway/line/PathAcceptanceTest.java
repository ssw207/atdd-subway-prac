package com.subway.subway.line;

import com.subway.subway.common.AcceptanceTest;
import com.subway.subway.line.dto.PathResponse;
import com.subway.subway.station.domain.Station;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.subway.subway.common.CommonStep.응답검증;
import static com.subway.subway.line.LineFixture.createLineSaveRequest;
import static com.subway.subway.line.LineStep.지하철노선_생성_요청;
import static com.subway.subway.line.PathStep.지하철_경로조회_요청;
import static com.subway.subway.line.SectionFixture.createSectionSaveRequest;
import static com.subway.subway.line.SectionStep.지하철구간_생성_요청;
import static com.subway.subway.station.StationStep.지하철역_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class PathAcceptanceTest extends AcceptanceTest {

    private Long 역1;
    private Long 역2;
    private Long 역3;
    private Long 역4;
    private Long 노선1;
    private Long 노선2;

    /**
     * 역1 - 노선1(2) - 역2
     * |             |
     * 노선2(3)         노선1(2)
     * |             |
     * 역4 - 노선2(3) - 역3
     */
    @Test
    void name() {
        역1 = 지하철역_생성_요청("역1").as(Long.class);
        역2 = 지하철역_생성_요청("역2").as(Long.class);
        역3 = 지하철역_생성_요청("역3").as(Long.class);
        역4 = 지하철역_생성_요청("역4").as(Long.class);

        노선1 = 지하철노선_생성_요청(createLineSaveRequest(역1, 역2, "노선1", 2)).as(Long.class);
        지하철구간_생성_요청(노선1, createSectionSaveRequest(역2, 역3, 2));

        노선2 = 지하철노선_생성_요청(createLineSaveRequest(역1, 역4, "노선2", 3)).as(Long.class);
        지하철구간_생성_요청(노선2, createSectionSaveRequest(역4, 역3, 3));
    }

    @Test
    void 경로조회() {
        ExtractableResponse<Response> response = 지하철_경로조회_요청(역1, 역3);

        응답검증(response, HttpStatus.OK);

        PathResponse path = response.as(PathResponse.class);

        assertThat(convertToStationIds(path)).containsExactly(역1, 역2, 역3);
        assertThat(path.distance()).isEqualTo(4);
    }

    private List<Long> convertToStationIds(PathResponse path) {
        return path.stations().stream().map(Station::getId).toList();
    }
}

