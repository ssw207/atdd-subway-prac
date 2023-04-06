package com.subway.subway.line.domian;

public class SectionRemoveFactory {

    public SectionRemoveAction createAction(Sections sections, Long station) {
        if (sections.isFirstStation(station)) {
            return new RemoveUpSectionRemoveAction(sections, station);
        }

        if (sections.isLastStation(station)) {
            return new RemoveDownSectionRemoveAction(sections, station);
        }

        throw new RuntimeException();
    }
}
