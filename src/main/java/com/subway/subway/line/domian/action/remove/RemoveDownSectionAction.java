package com.subway.subway.line.domian.action.remove;

import com.subway.subway.line.domian.Section;
import com.subway.subway.line.domian.Sections;

public class RemoveDownSectionAction extends AbstractRemoveSectionAction {

    private final Long stationIdForDelete;

    public RemoveDownSectionAction(Sections sections, Long stationIdForDelete) {
        super(sections);
        this.stationIdForDelete = stationIdForDelete;
    }

    @Override
    public void remove() {
        Section section = sections.findSectionByDownStation(stationIdForDelete);
        sections.forceRemove(section);
    }
}
