package com.subway.subway.line.domian;

class SectionRemoveFactory {

    public SectionRemoveAction createAction(Sections sections, Long station) {
        if (sections.isFirstStation(station)) {
            return new RemoveUpSectionAction(sections, station);
        }

        if (sections.isLastStation(station)) {
            return new RemoveDownSectionAction(sections, station);
        }

        return new RemoveMiddleSectionAction(sections, station);
    }
}
