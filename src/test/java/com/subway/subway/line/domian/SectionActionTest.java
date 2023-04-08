package com.subway.subway.line.domian;

import com.subway.subway.line.domian.action.SectionActionFactory;
import com.subway.subway.line.domian.action.add.AddMiddleSectionAction;
import com.subway.subway.line.domian.action.add.AddUpDownSectionAction;
import com.subway.subway.line.domian.action.add.SectionAddAction;
import com.subway.subway.line.domian.action.remove.RemoveDownSectionAction;
import com.subway.subway.line.domian.action.remove.RemoveUpSectionAction;
import com.subway.subway.line.domian.action.remove.SectionRemoveAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.subway.subway.line.SectionsFixture.createSection;
import static org.assertj.core.api.Assertions.assertThat;

class SectionActionTest {

    public static final long STATION_0 = 0L;
    public static final long STATION_1 = 1L;
    public static final long STATION_1_1 = 6L;
    public static final long STATION_2 = 3L;
    public static final long STATION_3 = 4L;
    public static final long STATION_4 = 5L;

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

    @Test
    void 상행역_추가_액션_생성() {
        SectionAddAction action = FACTORY.createAddAction(sections, createSection(STATION_0, STATION_1));
        assertThat(action).isInstanceOf(AddUpDownSectionAction.class);
    }

    @Test
    void 상행역_추가() {
        SectionAddAction action = FACTORY.createAddAction(sections, createSection(STATION_0, STATION_1));
        action.add();
        assertThat(sections.size()).isEqualTo(3);
    }

    @Test
    void 중간역_추가_액션_생성() {
        SectionAddAction action = FACTORY.createAddAction(sections, createSection(STATION_1, STATION_1_1));
        assertThat(action).isInstanceOf(AddMiddleSectionAction.class);
    }

    @Test
    void 중간역_추가() {
        SectionAddAction action = FACTORY.createAddAction(sections, createSection(STATION_1, STATION_1_1, 1));
        action.add();

        assertThat(sections.size()).isEqualTo(3);
        assertThat(sections.get(1).getDistance()).isEqualTo(9);
        assertThat(sections.get(2).getDistance()).isEqualTo(1);
    }


    @Test
    void 하행역_추가_액션_생성() {
        SectionAddAction action = FACTORY.createAddAction(sections, createSection(STATION_3, STATION_4));
        assertThat(action).isInstanceOf(AddUpDownSectionAction.class);
    }

    @Test
    void 하행역_추가() {
        SectionAddAction action = FACTORY.createAddAction(sections, createSection(STATION_3, STATION_4));
        action.add();
        assertThat(sections.size()).isEqualTo(3);
    }
}