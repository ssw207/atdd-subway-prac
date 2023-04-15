package com.subway.subway.line.domian.action;

import com.subway.subway.line.domian.Section;
import com.subway.subway.line.domian.Sections;
import com.subway.subway.line.domian.action.add.AddMiddleSectionAction;
import com.subway.subway.line.domian.action.add.AddUpDownSectionAction;
import com.subway.subway.line.domian.action.add.SectionAddAction;
import com.subway.subway.line.domian.action.remove.RemoveDownSectionAction;
import com.subway.subway.line.domian.action.remove.RemoveMiddleSectionAction;
import com.subway.subway.line.domian.action.remove.RemoveUpSectionAction;
import com.subway.subway.line.domian.action.remove.SectionRemoveAction;

import java.util.Optional;

public class SectionActionFactory {

    public SectionRemoveAction createRemoveAction(Sections sections, Long station) {
        if (sections.isFirstStation(station)) {
            return new RemoveUpSectionAction(sections, station);
        }

        if (sections.isLastStation(station)) {
            return new RemoveDownSectionAction(sections, station);
        }

        return new RemoveMiddleSectionAction(sections, station);
    }

    public SectionAddAction createAddAction(Sections sections, Section newSection) {
        Optional<Section> savedMiddleSection = sections.findMiddleSection(newSection);

        if (savedMiddleSection.isPresent()) {
            return new AddMiddleSectionAction(sections, newSection, savedMiddleSection.get());
        }

        return new AddUpDownSectionAction(sections, newSection);
    }
}