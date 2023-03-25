package com.subway.subway.line;

import com.subway.subway.line.dto.LineSaveRequest;

public class LineFixture {
    public static LineSaveRequest createLineSaveRequest() {
        return LineSaveRequest.builder()
                .name("1호선")
                .upStationId(1L)
                .downStationId(2L)
                .duration(10)
                .distance(11)
                .fare(1)
                .build();
    }
}
