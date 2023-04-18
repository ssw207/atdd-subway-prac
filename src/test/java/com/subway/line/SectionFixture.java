package com.subway.line;

import com.subway.line.domian.Section;
import com.subway.line.dto.SectionSaveRequest;
import com.subway.station.domain.Station;

public class SectionFixture {
    public static SectionSaveRequest createSectionSaveRequest(long upStationId, long downStationId, int distance) {
        return SectionSaveRequest.builder()
                .upStationId(upStationId)
                .downStationId(downStationId)
                .distance(distance)
                .build();
    }

    public static Section createSection(long upStationId, long downStationId) {
        return createSection(upStationId, downStationId, 10);
    }

    public static Section createSection(long upStationId, long downStationId, int distance) {
        return Section.builder()
                .upStation(new Station(upStationId))
                .downStation(new Station(downStationId))
                .distance(distance)
                .build();
    }
}
