package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotAddSectionException;
import com.subway.subway.station.domain.Station;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractSectionAddAction implements SectionAddAction {
    protected final Sections sections;
    protected final Section newSection;

    protected void validateCommonAddSection() {
        List<Station> cachedStations = sections.getStations();

        if (newSection.isSavedSection(cachedStations)) {
            throw new CanNotAddSectionException();
        }

        if (!sections.isEmpty() && newSection.isNotConnected(cachedStations)) {
            throw new CanNotAddSectionException();
        }
    }
}
