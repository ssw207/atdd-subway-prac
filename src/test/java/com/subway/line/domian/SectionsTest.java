package com.subway.line.domian;

import com.subway.common.exception.section.CanNotAddSectionException;
import com.subway.common.exception.section.CanNotRemoveSectionException;
import com.subway.line.LineFixture;
import com.subway.line.SectionFixture;
import com.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        Line line = LineFixture.createLine(10);
        line.add(SectionFixture.createSection(STATION_1, STATION_2));
        line.add(SectionFixture.createSection(STATION_2, STATION_3));
        this.sections = line.getSections();
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
        sections.add(SectionFixture.createSection(STATION_1, 2L, 1, 5));

        List<Long> ids = sections.getStations()
                .stream()
                .map(Station::getId)
                .toList();

        assertThat(ids).containsExactly(STATION_1, 2L, STATION_2, STATION_3);
    }

    @Test
    void 이미_등록된_구간을_등록할수_없음() {
        Section section = SectionFixture.createSection(STATION_1, STATION_2);
        assertThatThrownBy(() -> sections.add(section))
                .isInstanceOf(CanNotAddSectionException.class);
    }

    @Test
    void 연결되지_않은_구간은_등록할수_없음() {
        Section section = SectionFixture.createSection(10L, 11L);
        assertThatThrownBy(() -> sections.add(section))
                .isInstanceOf(CanNotAddSectionException.class);
    }

    @Test
    void 중간_구간의_길이가_기존_구간의_길이보가_길면_추가할수_없다() {
        Section section = SectionFixture.createSection(STATION_1, 2L, 100, 5);
        assertThatThrownBy(() -> sections.add(section))
                .isInstanceOf(CanNotAddSectionException.class);
    }

    @Test
    void 상행_구간_추가() {
        sections.add(SectionFixture.createSection(0L, STATION_1));
        assertThat(sections.size()).isEqualTo(3);
    }

    @Test
    void 중간_구간_추가() {
        sections.add(SectionFixture.createSection(STATION_1, 2L, 1, 5));

        assertThat(sections.size()).isEqualTo(3);
        assertThat(sections.get(0).getUpStationId()).isEqualTo(1L);
        assertThat(sections.get(0).getDistance()).isEqualTo(1);
        assertThat(sections.get(1).getUpStationId()).isEqualTo(2L);
        assertThat(sections.get(1).getDistance()).isEqualTo(9);
        assertThat(sections.get(2).getUpStationId()).isEqualTo(3L);
        assertThat(sections.get(2).getDistance()).isEqualTo(10);
    }

    @Test
    void 하행_구간_추가() {
        sections.add(SectionFixture.createSection(STATION_3, 5L));
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
                SectionFixture.createSectionForCalculateTotalFare(10, 1L),
                SectionFixture.createSectionForCalculateTotalFare(10, 2L),
                SectionFixture.createSectionForCalculateTotalFare(10, 1L));

        assertThat(new Sections(list).getTotalLineFare()).isEqualTo(20);
    }

    @Test
    void 노선_구간의_도착시간이_계산된다() {
        Section first = sections.get(0);
        Section second = sections.get(1);

        assertThat(first.getArriveTime()).isEqualTo(first.getLine().getStartTime().plusMinutes(first.getDuration()));
        assertThat(second.getArriveTime()).isEqualTo(first.getArriveTime().plusMinutes(second.getDuration()));
    }
}