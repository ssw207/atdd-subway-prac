package com.subway.subway.line;

import com.subway.subway.line.domian.Section;
import com.subway.subway.line.dto.SectionSaveRequest;
import com.subway.subway.station.domain.Station;

public class SectionsFixture {
    public static SectionSaveRequest createSectionSaveRequest(long upStationId, long downStationId) {
        return SectionSaveRequest.builder()
                .upStationId(upStationId)
                .downStationId(downStationId)
                .distance(10)
                .build();
    }

    public static Section createSection(long upStationId, long downStationId) {
        return Section.builder()
                .upStation(new Station(upStationId))
                .downStation(new Station(downStationId))
                .distance(10)
                .build();
    }
}
