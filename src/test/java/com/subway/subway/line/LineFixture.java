package com.subway.subway.line;

import com.subway.subway.line.domian.Line;
import com.subway.subway.line.dto.LineSaveRequest;
import com.subway.subway.line.dto.LineUpdateRequest;

import static com.subway.subway.line.SectionFixture.createSection;

public class LineFixture {
    public static LineSaveRequest createLineSaveRequest(long upStationId, long downStationId, String name, int distance) {
        return LineSaveRequest.builder()
                .name(name)
                .color("bg-red-600")
                .upStationId(upStationId)
                .downStationId(downStationId)
                .duration(10)
                .distance(distance)
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

    public static Line createLineHas2Section(long station1, long station2, long station3, int distance) {
        Line line = createLine();
        line.add(createSection(station1, station2, distance));
        line.add(createSection(station2, station3, distance));
        return line;
    }

    public static Line createLine() {
        return Line.builder()
                .id(1L)
                .name("노선1")
                .color("red")
                .fare(10)
                .build();
    }
}
