package com.subway.subway.line.domian;

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

    public SectionAddAction createAddAction(Sections sections, Section section) {
        return new AddUpDownSectionAction(sections, section);
    }
}
