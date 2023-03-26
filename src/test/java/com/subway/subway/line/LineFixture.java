package com.subway.subway.line;

import com.subway.subway.line.dto.LineSaveRequest;

public class LineFixture {
    public static LineSaveRequest createLineSaveRequest(long upStationId, long downStationId, String name) {
        return LineSaveRequest.builder()
                .name(name)
                .color("re")
                .upStationId(upStationId)
                .downStationId(downStationId)
                .duration(10)
                .distance(11)
                .fare(1)
                .build();
    }

    public static LineUpdateRequest createLineUpdateRequest(Long id, String name, String color) {
        return LineUpdateRequest.builder()
                .id(id)
                .name(name)
                .color(color)
                .build();
    }
}
