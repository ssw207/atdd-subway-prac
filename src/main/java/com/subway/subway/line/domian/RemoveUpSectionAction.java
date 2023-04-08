package com.subway.subway.line.domian;

public class RemoveUpSectionAction extends AbstractRemoveSectionAction {

    private final Long stationIdForDelete;

    public RemoveUpSectionAction(Sections sections, Long stationIdForDelete) {
        super(sections);
        this.stationIdForDelete = stationIdForDelete;
    }

    @Override
    public void remove() {
        sections.removeSectionByUpStation(stationIdForDelete);
    }
}
