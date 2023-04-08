package com.subway.subway.line.domian;

import java.util.Optional;

class SectionActionFactory {

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
