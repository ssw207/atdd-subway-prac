package com.subway.subway.line;

import com.subway.subway.line.dto.SectionSaveRequest;

public class SectionsFixture {
    public static SectionSaveRequest createSectionSaveRequest(long upStationId, long downStationId) {
        return SectionSaveRequest.builder()
                .upStationId(upStationId)
                .downStationId(downStationId)
                .distance(10)
                .build();
    }
}
