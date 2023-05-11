package com.subway.line;

import com.subway.common.AcceptanceTest;
import com.subway.common.doimain.ErrorResponseCode;
import com.subway.common.dto.SubwayResponse;
import com.subway.line.dto.LineResponse;
import com.subway.station.StationStep;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.subway.common.CommonStep.응답검증;
import static org.assertj.core.api.Assertions.assertThat;

public class SectionAcceptanceTest extends AcceptanceTest {

    private Long 역_0;
    private Long 역_1;
    private Long 역_2;
    private Long 역_3;
    private Long 열결되지_않은_역_1;
    private Long 열결되지_않은_역_2;
    private Long 노선;

    @BeforeEach
    void setUp() {
        역_0 = StationStep.지하철역_생성_요청("역0").as(Long.class);
        역_1 = StationStep.지하철역_생성_요청("역1").as(Long.class);
        역_2 = StationStep.지하철역_생성_요청("역2").as(Long.class);
        역_3 = StationStep.지하철역_생성_요청("역3").as(Long.class);
        열결되지_않은_역_1 = StationStep.지하철역_생성_요청("역3").as(Long.class);
        열결되지_않은_역_2 = StationStep.지하철역_생성_요청("역3").as(Long.class);
        노선 = LineStep.지하철노선_생성_요청(LineFixture.createLineSaveRequest(역_1, 역_2, "노선1", 3, 10)).as(LineResponse.class).id();
    }

    /**
     * given: 노선을 생성하고
     * when: 하행 구간을 추가하면
     * then: 구간이 추가되고
     * and: 노선을 다시 조회하면 추가한 구간의 역이 조회된다
     */
    @Test
    void 지하철_상행_구간_추가() {
        //when
        ExtractableResponse<Response> 지하철구간_생성_응답 = SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역_0, 역_1, 10));

        //then
        응답검증(지하철구간_생성_응답, HttpStatus.CREATED);
        LineStep.노선의_지하철역_검증(노선, 역_0, 역_1, 역_2);
    }

    /**
     * given: 노선을 생성하고
     * when: 중간 구간을 추가하면
     * then: 구간이 추가되고
     * and: 노선을 다시 조회하면 추가한 구간의 역이 조회된다
     */
    @Test
    void 지하철_중간_구간_추가() {
        //when
        ExtractableResponse<Response> 지하철구간_생성_응답 = SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역_1, 역_3, 1));

        //then
        응답검증(지하철구간_생성_응답, HttpStatus.CREATED);
        LineStep.노선의_지하철역_검증(노선, 역_1, 역_3, 역_2);
    }

    /**
     * given: 노선을 생성하고
     * when: 하행 구간을 추가하면
     * then: 구간이 추가되고
     * and: 노선을 다시 조회하면 추가한 구간의 역이 조회된다
     */
    @Test
    void 지하철_하행_구간_추가() {
        //when
        ExtractableResponse<Response> 지하철구간_생성_응답 = SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역_2, 역_3, 10));

        //then
        응답검증(지하철구간_생성_응답, HttpStatus.CREATED);
        LineStep.노선의_지하철역_검증(노선, 역_1, 역_2, 역_3);
    }

    @Test
    void 지하철_구간_추가_실패_같은_구간() {
        //when
        ExtractableResponse<Response> 지하철구간_생성_응답 = SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역_1, 역_2, 10));

        //then
        응답검증(지하철구간_생성_응답, HttpStatus.BAD_REQUEST);
        SubwayResponse subwayResponse = 지하철구간_생성_응답.as(SubwayResponse.class);
        assertThat(subwayResponse.code()).isEqualTo(ErrorResponseCode.CAN_NOT_ADD_SECTION_BY_SAME_SECTION.getCode());
    }

    @Test
    void 지하철_구간_추가_실패_연결되지_않은_구간() {
        //when
        ExtractableResponse<Response> 지하철구간_생성_응답 = SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(열결되지_않은_역_1, 열결되지_않은_역_2, 10));

        //then
        응답검증(지하철구간_생성_응답, HttpStatus.BAD_REQUEST);
        SubwayResponse subwayResponse = 지하철구간_생성_응답.as(SubwayResponse.class);
        assertThat(subwayResponse.code()).isEqualTo(ErrorResponseCode.CAN_NOT_ADD_SECTION_BY_NOT_CONNECTED.getCode());
    }

    @Test
    void 지하철_중간_구간_추가_실패_거리가_더_긴_구간() {
        //when
        ExtractableResponse<Response> 지하철구간_생성_응답 = SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역_0, 역_2, 20));

        //then
        응답검증(지하철구간_생성_응답, HttpStatus.BAD_REQUEST);
        SubwayResponse subwayResponse = 지하철구간_생성_응답.as(SubwayResponse.class);
        assertThat(subwayResponse.code()).isEqualTo(ErrorResponseCode.CAN_NOT_ADD_MIDDLE_SECTION_BY_DISTANCE_LONGER.getCode());
    }

    /**
     * given: 지하철 노선을 등록하고
     * when: 지하철 구간을 삭제하면
     * then: 삭제에 실패한다
     */
    @Test
    void 지하철노선의_구간이_하나면_삭제불가() {
        //when
        ExtractableResponse<Response> 지하철구간_삭제응답 = SectionStep.지하철_구간_삭제_요청(노선, 역_2);

        //then
        응답검증(지하철구간_삭제응답, HttpStatus.BAD_REQUEST);
    }

    /**
     * given: 지하철 노선을 등록하고
     * when: 지하철에 없는 역을 삭제하면
     * then: 삭제에 실패한다
     */
    @Test
    void 지하철노선에_없는_역을_삭제하면_삭제에_실패한다() {
        //when
        ExtractableResponse<Response> 지하철구간_삭제응답 = SectionStep.지하철_구간_삭제_요청(노선, 100L);

        //then
        응답검증(지하철구간_삭제응답, HttpStatus.BAD_REQUEST);
    }

    /**
     * given: 지하철 노선과 구간을 등록하고
     * when: 상행역을 제거하고
     * and : 노선을 다시 조회하면
     * then: 상행역이 삭제된다
     */
    @Test
    void 지하철_상행_구간_삭제() {
        //given
        SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역_2, 역_3, 10));

        //when
        ExtractableResponse<Response> 지하철구간_삭제응답 = SectionStep.지하철_구간_삭제_요청(노선, 역_1);

        //then
        응답검증(지하철구간_삭제응답, HttpStatus.OK);
        LineStep.노선의_지하철역_검증(노선, 역_2, 역_3);
    }

    /**
     * given: 지하철 노선과 구간을 등록하고
     * when: 중간역을 제거하고
     * and : 노선을 다시 조회하면
     * then: 중간역이 삭제되고 거리는 두 구간의 합이 된다
     */
    @Test
    void 지하철_중간_구간_삭제() {
        //given
        SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역_2, 역_3, 10));

        //when
        ExtractableResponse<Response> 지하철구간_삭제응답 = SectionStep.지하철_구간_삭제_요청(노선, 역_2);

        //then
        응답검증(지하철구간_삭제응답, HttpStatus.OK);
        LineStep.노선의_지하철역_검증(노선, 역_1, 역_3);
    }

    /**
     * given: 지하철 노선과 구간을 등록하고
     * when: 하행역을 제거하고
     * and : 노선을 다시 조회하면
     * then: 하행역이 삭제된다
     */
    @Test
    void 지하철_하행_구간_삭제() {
        //given
        SectionStep.지하철구간_생성_요청(노선, SectionFixture.createSectionSaveRequest(역_2, 역_3, 10));

        //when
        ExtractableResponse<Response> 지하철구간_삭제응답 = SectionStep.지하철_구간_삭제_요청(노선, 역_3);

        //then
        응답검증(지하철구간_삭제응답, HttpStatus.OK);
        LineStep.노선의_지하철역_검증(노선, 역_1, 역_2);
    }
}
