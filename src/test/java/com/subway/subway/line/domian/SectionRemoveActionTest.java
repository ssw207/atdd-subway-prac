package com.subway.subway.line.domian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.subway.subway.line.SectionsFixture.createSection;
import static org.assertj.core.api.Assertions.assertThat;

class SectionRemoveActionTest {

    public static final long STATION_1 = 1L;
    public static final long STATION_2 = 3L;
    public static final long STATION_3 = 4L;
    public static final SectionActionFactory FACTORY = new SectionActionFactory();
    private Sections sections;

    @BeforeEach
    void setUp() {
        Sections sections = new Sections();
        sections.add(createSection(STATION_1, STATION_2));
        sections.add(createSection(STATION_2, STATION_3));

        this.sections = sections;
    }

    @Test
    void 상행역_삭제_액션_생성() {
        SectionRemoveAction action = FACTORY.createRemoveAction(sections, STATION_1);
        assertThat(action).isInstanceOf(RemoveUpSectionAction.class);
    }

    @Test
    void 상행역_삭제() {
        SectionRemoveAction action = FACTORY.createRemoveAction(sections, STATION_1);
        action.remove();

        assertThat(sections.size()).isEqualTo(1);
        assertThat(sections.getStations().get(0).getId()).isEqualTo(STATION_2);
    }

    @Test
    void 하행역_삭제_액션_생성() {
        SectionRemoveAction action = FACTORY.createRemoveAction(sections, STATION_3);
        assertThat(action).isInstanceOf(RemoveDownSectionAction.class);
    }

    @Test
    void 하행역_삭제() {
        SectionRemoveAction action = FACTORY.createRemoveAction(sections, STATION_3);
        action.remove();

        assertThat(sections.size()).isEqualTo(1);
        assertThat(sections.getStations().get(0).getId()).isEqualTo(STATION_1);
    }
}