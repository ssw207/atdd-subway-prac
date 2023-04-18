package com.subway.line.domian.action;

import com.subway.line.domian.Section;
import com.subway.line.domian.Sections;
import com.subway.line.domian.action.add.AddMiddleSectionAction;
import com.subway.line.domian.action.add.AddUpDownSectionAction;
import com.subway.line.domian.action.add.SectionAddAction;
import com.subway.line.domian.action.remove.RemoveDownSectionAction;
import com.subway.line.domian.action.remove.RemoveMiddleSectionAction;
import com.subway.line.domian.action.remove.RemoveUpSectionAction;
import com.subway.line.domian.action.remove.SectionRemoveAction;

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
        return sections.findMiddleSection(newSection)
                .map(s -> (SectionAddAction) new AddMiddleSectionAction(sections, newSection, s))
                .orElseGet(() -> new AddUpDownSectionAction(sections, newSection));
    }
}
