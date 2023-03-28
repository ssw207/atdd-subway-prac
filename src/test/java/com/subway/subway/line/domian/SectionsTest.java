package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SectionsTest {

    @Test
    void 역목록_조회() {
        Sections sections = new Sections();
        sections.add(createSection(0L, 1L));
        sections.add(createSection(1L, 2L));

        List<Long> ids = sections.getStations().stream().map(Station::getId).collect(Collectors.toList());
        assertThat(ids).containsExactly(0L, 1L, 2L);
    }

    private Section createSection(long upStationId, long downStationId) {
        return Section.builder()
                .upStation(new Station(upStationId))
                .downStation(new Station(downStationId))
                .build();
    }
}