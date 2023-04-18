package com.subway.line.dto;

import com.subway.line.domian.Section;
import com.subway.station.domain.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.LongFunction;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SectionSaveRequest {

    private long upStationId;
    private long downStationId;
    private int distance;

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
