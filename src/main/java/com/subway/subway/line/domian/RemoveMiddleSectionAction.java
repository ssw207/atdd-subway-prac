package com.subway.subway.line.domian;

public class RemoveMiddleSectionAction extends AbstractRemoveSectionAction {

    private final Long stationIdForDelete;

    public RemoveMiddleSectionAction(Sections sections, Long stationIdForDelete) {
        super(sections);
        this.stationIdForDelete = stationIdForDelete;
    }

    @Override
    public void remove() {
        Section removeTarget = sections.findSectionByUpStation(stationIdForDelete);
        addDistanceToBeforeSection(removeTarget);
        sections.remove(removeTarget);
    }

    private void addDistanceToBeforeSection(Section removeTarget) {
        sections.findSectionByDownStation(removeTarget.getUpStationId())
                .addDistance(removeTarget.getDistance());
    }
}
