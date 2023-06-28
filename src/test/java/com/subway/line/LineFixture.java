package com.subway.line;

import com.subway.line.domian.Line;
import com.subway.line.domian.Section;
import com.subway.line.dto.LineSaveRequest;
import com.subway.line.dto.LineUpdateRequest;
import com.subway.station.domain.Station;

import java.time.LocalTime;

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
                .startTime(LocalTime.of(05, 00, 00))
                .endTime(LocalTime.of(23, 00, 00))
                .term(600)
                .build();
    }

    public static LineUpdateRequest createLineUpdateRequest(Long id, String name, String color) {
        return LineUpdateRequest.builder()
                .id(id)
                .name(name)
                .color(color)
                .build();
    }

    public static Line createLineHas2Section(long station1, long station2, long station3, int distance, int duration, int lineFare) {
        Line line = createLine(lineFare);
        line.add(Section.builder()
                .upStation(new Station(station1, "역" + station1))
                .downStation(new Station(station2, "역" + station2))
                .distance(distance)
                .duration(duration)
                .build());
        line.add(Section.builder()
                .upStation(new Station(station2, "역" + station2))
                .downStation(new Station(station3, "역" + station3))
                .distance(distance)
                .duration(duration)
                .build());
        return line;
    }

    public static Line createLine(int lineFare) {
        return Line.builder()
                .id(1L)
                .name("노선1")
                .color("red")
                .fare(lineFare)
                .startTime(LocalTime.of(05, 00, 00))
                .endTime(LocalTime.of(23, 00, 00))
                .term(600)
                .build();
    }
}
