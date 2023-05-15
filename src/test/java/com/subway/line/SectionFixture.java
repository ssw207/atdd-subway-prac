package com.subway.line;

import com.subway.line.domian.Line;
import com.subway.line.domian.Section;
import com.subway.line.dto.SectionSaveRequest;
import com.subway.station.domain.Station;

public class SectionFixture {
    public static SectionSaveRequest createSectionSaveRequest(long upStationId, long downStationId, int distance, int duration) {
        return SectionSaveRequest.builder()
                .upStationId(upStationId)
                .downStationId(downStationId)
                .distance(distance)
                .duration(duration)
                .build();
    }

    public static Section createSection(long upStationId, long downStationId) {
        return createSection(upStationId, downStationId, 10, 5);
    }

    public static Section createSection(long upStationId, long downStationId, int distance, int duration) {
        return Section.builder()
                .upStation(new Station(upStationId))
                .downStation(new Station(downStationId))
                .distance(distance)
                .duration(duration)
                .build();
    }

    public static Section createSectionForCalculateTotalFare(int fare, long id) {
        return Section.builder()
                .line(Line.builder()
                        .id(id)
                        .fare(fare)
                        .build())
                .build();
    }
}
