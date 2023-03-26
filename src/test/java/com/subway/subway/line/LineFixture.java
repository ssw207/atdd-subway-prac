package com.subway.subway.line;

import com.subway.subway.line.dto.LineSaveRequest;

public class LineFixture {
    public static LineSaveRequest createLineSaveRequest(long upStationId, long downStationId, String name) {
        return LineSaveRequest.builder()
                .name(name)
                .upStationId(upStationId)
                .downStationId(downStationId)
                .duration(10)
                .distance(11)
                .fare(1)
                .build();
    }
}
