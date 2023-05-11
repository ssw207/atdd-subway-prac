package com.subway.line;

import com.subway.common.AcceptanceTest;
import com.subway.common.doimain.ErrorResponseCode;
import com.subway.common.dto.SubwayResponse;
import com.subway.line.dto.LineResponse;
import com.subway.line.dto.PathResponse;
import com.subway.station.StationStep;
import com.subway.station.dto.StationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.subway.common.CommonStep.응답검증;
import static com.subway.line.SectionStep.지하철구간_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class PathAcceptanceTest extends AcceptanceTest {

    private static final long 없는역1 = 99L;
    private static final long 없는역2 = 88L;

    private Long 역1;
    private Long 역2;
    private Long 역3;
    private Long 역4;
    private Long 노선1;
    private Long 노선2;
    private Long 역_미연결1;
    private Long 역_미연결2;


    /**
     * 역1 - 노선1(2) - 역2
     * |             |
     * 노선2(3)         노선1(2)
     * |             |
     * 역4 - 노선2(3) - 역3
     */
    @BeforeEach
    void setUp() {
        역1 = StationStep.지하철역_생성_요청("역1").as(Long.class);
        역2 = StationStep.지하철역_생성_요청("역2").as(Long.class);
        역3 = StationStep.지하철역_생성_요청("역3").as(Long.class);
        역4 = StationStep.지하철역_생성_요청("역4").as(Long.class);
        역_미연결1 = StationStep.지하철역_생성_요청("역5").as(Long.class);
        역_미연결2 = StationStep.지하철역_생성_요청("역6").as(Long.class);

        노선1 = LineStep.지하철노선_생성_요청(LineFixture.createLineSaveRequest(역1, 역2, "노선1", 2)).as(LineResponse.class).getId();
        지하철구간_생성_요청(노선1, SectionFixture.createSectionSaveRequest(역2, 역3, 2));

        노선2 = LineStep.지하철노선_생성_요청(LineFixture.createLineSaveRequest(역1, 역4, "노선2", 3)).as(LineResponse.class).getId();
        지하철구간_생성_요청(노선2, SectionFixture.createSectionSaveRequest(역4, 역3, 3));

        LineStep.지하철노선_생성_요청(LineFixture.createLineSaveRequest(역_미연결1, 역_미연결2, "노선3", 3));
    }

    @Test
    void 최단거리_경로조회() {
        ExtractableResponse<Response> response = PathStep.지하철_경로조회_요청(역1, 역3);

        응답검증(response, HttpStatus.OK);

        PathResponse path = response.as(PathResponse.class);

        assertThat(convertToStationIds(path)).containsExactly(역1, 역2, 역3);
        assertThat(path.distance()).isEqualTo(4);
    }

    /**
     * Feature: 지하철 경로 검색
     * Scenario: 두 역의 최소 시간 경로를 조회
     * Given 지하철역이 등록되어있음
     * And 지하철 노선이 등록되어있음
     * And 지하철 노선에 지하철역이 등록되어있음
     * When 출발역에서 도착역까지의 최소 시간 기준으로 경로 조회를 요청
     * Then 최소 시간 기준 경로를 응답
     * And 총 거리와 소요 시간을 함께 응답함
     */
    @Test
    void 최단시간_경로조회() {
        //when
        ExtractableResponse<Response> response = PathStep.지하철_경로조회_요청(역1, 역3);
        응답검증(response, HttpStatus.OK);

        //then
        PathResponse path = response.as(PathResponse.class);
        assertThat(convertToStationIds(path)).containsExactly(역1, 역4, 역3);
        assertThat(path.distance()).isEqualTo(4);
        assertThat(path.duration()).isEqualTo(10);
    }

    @Test
    void 출발역과_도착역이_같은경우_예외() {
        ExtractableResponse<Response> response = PathStep.지하철_경로조회_요청(역1, 역1);
        응답검증(response, HttpStatus.BAD_REQUEST);
        SubwayResponse subwayResponse = response.as(SubwayResponse.class);
        assertThat(subwayResponse.code()).isEqualTo(ErrorResponseCode.CAN_NOT_FIND_PATH_BY_SAME_STATION.getCode());
    }

    @Test
    void 출발역과_도착역이_연결되지_않은경우_예외() {
        ExtractableResponse<Response> response = PathStep.지하철_경로조회_요청(역1, 역_미연결2);
        응답검증(response, HttpStatus.BAD_REQUEST);
        SubwayResponse subwayResponse = response.as(SubwayResponse.class);
        assertThat(subwayResponse.code()).isEqualTo(ErrorResponseCode.CAN_NOT_FIND_PATH_BY_NOT_CONNECTED.getCode());
    }

    @Test
    void 없는_역을_입력한_꼉우_예외() {
        ExtractableResponse<Response> response = PathStep.지하철_경로조회_요청(없는역1, 없는역2);
        응답검증(response, HttpStatus.BAD_REQUEST);
        SubwayResponse subwayResponse = response.as(SubwayResponse.class);
        assertThat(subwayResponse.code()).isEqualTo(ErrorResponseCode.CAN_NOT_FIND_PATH_BY_NOT_EXISTS_STATION.getCode());
    }

    private List<Long> convertToStationIds(PathResponse path) {
        return path.stations().stream().map(StationResponse::id).toList();
    }
}

