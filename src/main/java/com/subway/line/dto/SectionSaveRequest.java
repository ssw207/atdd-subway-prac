package com.subway.line.dto;

import com.subway.line.domian.Section;
import com.subway.station.domain.Station;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.function.LongFunction;

@Builder
public record SectionSaveRequest(@NotNull Long upStationId, @NotNull Long downStationId, int distance) {

    public Section toEntity(LongFunction<Station> findStationFunction) {
        Station downStation = findStationFunction.apply(downStationId);
        Station upStation = findStationFunction.apply(upStationId);

        return Section.builder()
                .upStation(upStation)
                .downStation(downStation)
                .distance(distance)
                .build();
    }
}
