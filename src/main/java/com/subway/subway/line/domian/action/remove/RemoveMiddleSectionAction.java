package com.subway.subway.line.domian.action.remove;

import com.subway.subway.line.domian.Section;
import com.subway.subway.line.domian.Sections;

public class RemoveMiddleSectionAction extends AbstractRemoveSectionAction {

    private final Long stationIdForDelete;

    public RemoveMiddleSectionAction(Sections sections, Long stationIdForDelete) {
        super(sections);
        this.stationIdForDelete = stationIdForDelete;
    }

    @Override
    public void remove() {
        validate();

        Section removeTarget = sections.findSectionByUpStation(stationIdForDelete);
        Section beforeSection = sections.findSectionByDownStation(removeTarget.getUpStationId());
        adjustBeforeSection(beforeSection, removeTarget);
        sections.forceRemove(removeTarget);
    }

    private void adjustBeforeSection(Section beforeSection, Section removeTarget) {
        beforeSection.addDistance(removeTarget.getDistance());
        beforeSection.changeDownStation(removeTarget.getDownStation());
    }
}
