package com.subway.line;

import com.subway.line.domian.Line;
import com.subway.line.dto.LineSaveRequest;
import com.subway.line.dto.LineUpdateRequest;

import static com.subway.line.SectionFixture.createSection;

public class LineFixture {
    public static LineSaveRequest createLineSaveRequest(long upStationId, long downStationId, String name, int distance, int duration, int lineFare) {
        return LineSaveRequest.builder()
                .name(name)
                .color("bg-red-600")
                .upStationId(upStationId)
                .downStationId(downStationId)
                .duration(duration)
                .distance(distance)
                .fare(lineFare)
                .build();
    }

    public static LineUpdateRequest createLineUpdateRequest(Long id, String name, String color) {
        return LineUpdateRequest.builder()
                .id(id)
                .name(name)
                .color(color)
                .build();
    }

    public static Line createLineHas2Section(long station1, long station2, long station3, int distance, int duration) {
        Line line = createLine();
        line.add(createSection(station1, station2, distance, duration));
        line.add(createSection(station2, station3, distance, duration));
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
