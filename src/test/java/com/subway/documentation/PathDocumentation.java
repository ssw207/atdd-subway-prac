package com.subway.documentation;

import com.subway.line.domian.Path;
import com.subway.line.domian.PathType;
import com.subway.line.domian.fare.Fare;
import com.subway.line.servcie.PathService;
import com.subway.station.domain.Station;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

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

        given(spec).log().all()
                .filter(document("path",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("source", 1L)
                .queryParam("target", 2L)
                .queryParam("type", PathType.DISTANCE.getCode())
                .when().get("/paths")
                .then().log().all().extract();
    }
}
