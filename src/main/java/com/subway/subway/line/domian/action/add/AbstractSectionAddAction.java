package com.subway.subway.line.domian.action.add;

import com.subway.subway.common.exception.section.CanNotAddSectionExceptionByAlreadySaved;
import com.subway.subway.common.exception.section.CanNotAddSectionExceptionByNotConnected;
import com.subway.subway.line.domian.Section;
import com.subway.subway.line.domian.Sections;
import com.subway.subway.station.domain.Station;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractSectionAddAction implements SectionAddAction {
    protected final Sections sections;
    protected final Section newSection;

    protected void validate() {
        List<Station> cachedStations = sections.getStations();

        if (newSection.isSavedSection(cachedStations)) {
            throw new CanNotAddSectionExceptionByAlreadySaved();
        }

        if (!sections.isEmpty() && newSection.isNotConnected(cachedStations)) {
            throw new CanNotAddSectionExceptionByNotConnected(createNotConnectedErrorMessage(cachedStations));
        }
    }

    private String createNotConnectedErrorMessage(List<Station> cachedStations) {
        return String.format("노선의 역ID :%s, 추가하려는 상행역 ID : %d, 추가하려는 하행열 iD : %d",
                toStationIds(cachedStations),
                newSection.getUpStationId(),
                newSection.getDownStationId());
    }

    private List<Long> toStationIds(List<Station> cachedStations) {
        return cachedStations.stream().map(Station::getId).toList();
    }
}
