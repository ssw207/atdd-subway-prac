package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotAddSectionException;
import com.subway.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.subway.subway.line.SectionsFixture.createSection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SectionsTest {

    public static final long STATION_1 = 1L;
    public static final long STATION_2 = 3L;
    public static final long STATION_3 = 4L;
    private Sections sections;

    @BeforeEach
    void setUp() {
        Sections sections = new Sections();
        sections.add(createSection(STATION_1, STATION_2));
        sections.add(createSection(STATION_2, STATION_3));

        this.sections = sections;
    }

    @Test
    void 역목록_조회() {
        List<Long> ids = sections.getStations()
                .stream()
                .map(Station::getId)
                .toList();

        assertThat(ids).containsExactly(STATION_1, STATION_2, STATION_3);
    }

    @Test
    void 이미_등록된_구간을_등록할수_없음() {
        Section section = createSection(STATION_1, STATION_2);
        assertThatThrownBy(() -> sections.add(section))
                .isInstanceOf(CanNotAddSectionException.class);
    }

    @Test
    void 연결되지_않은_구간은_등록할수_없음() {
        Section section = createSection(10L, 11L);
        assertThatThrownBy(() -> sections.add(section))
                .isInstanceOf(CanNotAddSectionException.class);
    }

    @Test
    void 중간_구간의_길이가_기존_구간의_길이보가_길면_추가할수_없다() {
        Section section = createSection(STATION_1, 2L, 100);
        assertThatThrownBy(() -> sections.add(section))
                .isInstanceOf(CanNotAddSectionException.class);
    }
    
    @Test
    void 상행_구간_추가() {
        sections.add(createSection(0L, STATION_1));
        assertThat(sections.size()).isEqualTo(3);
    }

    @Test
    void 중간_구간_추가() {
        sections.add(createSection(STATION_1, 2L, 1));
        assertThat(sections.size()).isEqualTo(3);
    }

    @Test
    void 하행_구간_추가() {
        sections.add(createSection(STATION_3, 5L));
        assertThat(sections.size()).isEqualTo(3);
    }
}