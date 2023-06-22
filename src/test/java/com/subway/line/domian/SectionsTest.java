package com.subway.line.domian;

import com.subway.common.exception.section.CanNotAddSectionException;
import com.subway.common.exception.section.CanNotRemoveSectionException;
import com.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.subway.line.SectionFixture.createSection;
import static com.subway.line.SectionFixture.createSectionForCalculateTotalFare;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SectionsTest {

    private static final long STATION_1 = 1L;
    private static final long STATION_1_1 = 2L;
    private static final long STATION_2 = 3L;
    private static final long STATION_3 = 4L;

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
    void 역목록은_구간순서대로_정렬된다() {
        sections.add(createSection(STATION_1, STATION_1_1, 1, 5));

        List<Long> ids = sections.getStations()
                .stream()
                .map(Station::getId)
                .toList();

        assertThat(ids).containsExactly(STATION_1, STATION_1_1, STATION_2, STATION_3);
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
        Section section = createSection(STATION_1, STATION_1_1, 100, 5);
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
        sections.add(createSection(STATION_1, STATION_1_1, 1, 5));

        assertThat(sections.size()).isEqualTo(3);
        assertThat(sections.get(1).getDistance()).isEqualTo(9);
        assertThat(sections.get(2).getDistance()).isEqualTo(1);
    }

    @Test
    void 하행_구간_추가() {
        sections.add(createSection(STATION_3, 5L));
        assertThat(sections.size()).isEqualTo(3);
    }

    @Test
    void 처음_구간_삭제() {
        sections.remove(STATION_1);
        assertThat(sections.size()).isEqualTo(1);
        assertThat(sections.get(0).getDistance()).isEqualTo(10);
    }

    @Test
    void 중간_구간_삭제() {
        sections.remove(STATION_2);
        assertThat(sections.size()).isEqualTo(1);
        assertThat(sections.get(0).getDistance()).isEqualTo(20);
    }

    @Test
    void 마지막_구간_삭제() {
        sections.remove(STATION_3);
        assertThat(sections.size()).isEqualTo(1);
        assertThat(sections.get(0).getDistance()).isEqualTo(10);
    }

    @Test
    void 구간이_하나면_삭제_불가() {
        sections.remove(STATION_3);

        assertThatThrownBy(() -> sections.remove(STATION_2))
                .isInstanceOf(CanNotRemoveSectionException.class);
    }

    @Test
    void 총_노선요금_계산() {
        List<Section> list = List.of(
                createSectionForCalculateTotalFare(10, 1L),
                createSectionForCalculateTotalFare(10, 2L),
                createSectionForCalculateTotalFare(10, 1L));

        assertThat(new Sections(list).getTotalLineFare()).isEqualTo(20);
    }
}