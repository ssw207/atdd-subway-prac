package com.subway.documentation;

import com.subway.line.LineFixture;
import com.subway.line.domian.Line;
import com.subway.line.servcie.LineService;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.subway.line.LineStep.지하철노선_목록_조회_요청;
import static com.subway.line.LineStep.지하철노선_조회_요청;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

public class LineDocument extends Documentation {

    @MockBean
    private LineService lineService;

    @Test
    void 지하철노선_목록_조회() {
        Line line = LineFixture.createLineHas2Section(1L, 2L, 3L, 10, 20, 200);
        when(lineService.findAll()).thenReturn(List.of(line));

        RequestSpecification specification = given(spec).log().all()
                .filter(document("lines",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("지하철 노선 아이디"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("지하철 노선 이름"),
                                fieldWithPath("[].color").type(JsonFieldType.STRING).description("지하철 노선 색깔"),
                                fieldWithPath("[].fare").type(JsonFieldType.NUMBER).description("지하철 노선 추가 요금"),
                                fieldWithPath("[].stations").type(JsonFieldType.ARRAY).description("지하철 노선의 역"),
                                fieldWithPath("[].stations[].id").type(JsonFieldType.NUMBER).description("역 아이디"),
                                fieldWithPath("[].stations[].name").type(JsonFieldType.STRING).description("역 이름"))))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        지하철노선_목록_조회_요청(specification);
    }

    @Test
    void 지하철노선_조회() {
        Line line = LineFixture.createLineHas2Section(1L, 2L, 3L, 10, 20, 200);
        when(lineService.findById(1L)).thenReturn(line);

        RequestSpecification specification = given(spec).log().all()
                .filter(document("line",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("지하철 노선 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("지하철 노선 이름"),
                                fieldWithPath("color").type(JsonFieldType.STRING).description("지하철 노선 색깔"),
                                fieldWithPath("fare").type(JsonFieldType.NUMBER).description("지하철 노선 추가 요금"),
                                fieldWithPath("stations").type(JsonFieldType.ARRAY).description("지하철 노선의 역"),
                                fieldWithPath("stations[].id").type(JsonFieldType.NUMBER).description("역 아이디"),
                                fieldWithPath("stations[].name").type(JsonFieldType.STRING).description("역 이름"))))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        지하철노선_조회_요청(specification, 1L);
    }
}
