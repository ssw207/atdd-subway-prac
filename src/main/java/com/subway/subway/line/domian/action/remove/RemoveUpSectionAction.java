package com.subway.subway.line.domian.action.remove;

import com.subway.subway.line.domian.Section;
import com.subway.subway.line.domian.Sections;

public class RemoveUpSectionAction extends AbstractRemoveSectionAction {

    private final Long stationIdForDelete;

    public RemoveUpSectionAction(Sections sections, Long stationIdForDelete) {
        super(sections);
        this.stationIdForDelete = stationIdForDelete;
    }

    @Override
    public void remove() {
        validate();
        
        Section section = sections.findSectionByUpStation(stationIdForDelete);
        sections.forceRemove(section);
    }
}
