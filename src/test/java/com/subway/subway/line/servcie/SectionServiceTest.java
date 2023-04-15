package com.subway.subway.line.servcie;

import com.subway.subway.line.LineFixture;
import com.subway.subway.line.domian.Line;
import com.subway.subway.line.dto.SectionSaveRequest;
import com.subway.subway.station.domain.Station;
import com.subway.subway.station.service.StationService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SectionServiceTest {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private LineService lineService;

    @Autowired
    private StationService stationService;

    @Autowired
    private EntityManager em;

    @Test
    void save() {
        Long 역1 = stationService.save(new Station("역1"));
        Long 역2 = stationService.save(new Station("역2"));
        Line line = lineService.save(LineFixture.createLineSaveRequest(역1, 역2, "노선", 10));

        Long 역3 = stationService.save(new Station("역3"));
        sectionService.saveSection(line.getId(), new SectionSaveRequest(역2, 역3, 10));

        em.flush();
        em.clear();

        Line find = lineService.findById(line.getId());

        List<Station> stations = find.getStations();
        assertThat(stations.size()).isEqualTo(3);
        assertThat(stations.stream().map(Station::getId).toList()).containsExactly(1L, 2L, 3L);
    }
}