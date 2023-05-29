package com.subway.documentation;

import com.subway.line.domian.Path;
import com.subway.line.domian.PathType;
import com.subway.line.domian.fare.Fare;
import com.subway.line.servcie.PathService;
import com.subway.station.domain.Station;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.subway.line.PathStep.지하철_경로조회_요청;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;

/**
 * 문서화 관련 공식문서 참고
 * - https://docs.spring.io/spring-restdocs/docs/3.0.0/reference/htmlsingle/#documenting-your-api
 */
@ExtendWith(MockitoExtension.class)
public class PathDocumentation extends Documentation {

    @MockBean
    private PathService pathService; // 기능 검증이 목적이 아니므로 mocking한다

    @MockBean
    private Fare fare; // 기능 검증이 목적이 아니므로 mocking한다

    @Mock
    private Path path;

    @Test
    void path() {
        when(pathService.findPath(anyLong(), anyLong(), any())).thenReturn(path);

        when(path.getStations()).thenReturn(Lists.newArrayList(new Station(1L, "강남역"), new Station(2L, "역삼역")));
        when(path.getDistance()).thenReturn(10);
        when(path.getDuration()).thenReturn(20);
        when(path.getTotalLineFare()).thenReturn(0);

        when(fare.calculate(any())).thenReturn(1250);

        지하철_경로조회_요청(getRequestSpecification(), "", 1L, 2L, PathType.DISTANCE);
    }

    private RequestSpecification getRequestSpecification() {
        return given(spec).log().all()
                .filter(document("path", // 스니펫이 생성되는 폴더명
                        queryParameters(  // query-parameters 스니펫을 만든다. 사용하는 메서드에 따라 생성되는 스니펫이 다르다.
                                parameterWithName("source") // 필드명
                                        .description("출발역 아이디") // 필드설명
                                        .attributes(key("optional").value("true")), // 기본 스니펫에는 없는 커스텀값 지정 (추가 템플릿 설정이 필요하다)
                                parameterWithName("target")
                                        .description("도착역 아이디")
                                        .attributes(key("optional").value("true")),
                                parameterWithName("type").description("최단거리 조회 기준(거리, 시간등)")
                                        .attributes(key("optional").value("true"))),
                        responseFields( // 응답 필드를 정의. response-fields 스니펫을 만든다
                                fieldWithPath("stations").type(JsonFieldType.ARRAY).description("경로 지하철역 목록"),
                                fieldWithPath("stations[].id").type(JsonFieldType.NUMBER).description("지하철역 아이디"),
                                fieldWithPath("stations[].name").type(JsonFieldType.STRING).description("지하철역 이름"),
                                fieldWithPath("duration").type(JsonFieldType.NUMBER).description("소요시간(분)"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("거리(km)"),
                                fieldWithPath("fare").type(JsonFieldType.NUMBER).description("요금"))))
                .accept(MediaType.APPLICATION_JSON_VALUE);
    }
}
