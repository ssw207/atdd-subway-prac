package com.subway.subway.line.domian;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveMiddleSectionAction implements SectionRemoveAction {

    private final Sections sections;
    private final Long stationIdForDelete;

    @Override
    public void remove() {
        sections.removeSectionByMiddleStation(stationIdForDelete);
    }
}
