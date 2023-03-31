package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotAddSectionException;
import com.subway.subway.line.SectionsFixture;
import com.subway.subway.station.domain.Station;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.subway.subway.line.SectionsFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SectionsTest {

    @Test
    void 역목록_조회() {
        Sections sections = new Sections();
        sections.add(createSection(0L, 1L));
        sections.add(createSection(1L, 2L));

        List<Long> ids = sections.getStations().stream().map(Station::getId).collect(Collectors.toList());
        assertThat(ids).containsExactly(0L, 1L, 2L);
    }

    @Test
    void 이미_등록된_구간을_등록할수_없음() {
        // given: 구간을 2개 저장한다.
        Sections sections = new Sections();
        sections.add(createSection(0L, 1L));
        sections.add(createSection(1L, 2L));

        // when: 추가하려는 구간의 상행역과 하행역이 이미 구간에 등록되어 있으면
        Section newSection = createSection(0L, 2L);

        // then: 저장에 실패한다.
        assertThatThrownBy(() -> sections.add(newSection)).isInstanceOf(CanNotAddSectionException.class);
    }

    @Test
    void 연결되지_않은_구간은_등록할수_없음() {
        // given: 구간을 2개 저장한다.
        Sections sections = new Sections();
        sections.add(createSection(0L, 1L));
        sections.add(createSection(1L, 2L));

        // when: 연결되지 않은 구간을 추가하면
        Section newSection = createSection(3L, 4L);

        // then: 저장에 실패한다.
        assertThatThrownBy(() -> sections.add(newSection)).isInstanceOf(CanNotAddSectionException.class);
    }
}