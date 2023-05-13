package com.subway.line.dto;

import com.subway.line.domian.Line;
import com.subway.line.domian.Section;
import com.subway.station.domain.Station;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.function.LongFunction;

@Builder
public record LineSaveRequest(
        @NotNull String name,
        String color,
        @NotNull Long upStationId,
        @NotNull Long downStationId,
        int duration,
        int distance,
        int fare) {

    public Line toEntity(LongFunction<Station> findStationFunction) {
        Station upStation = findStationFunction.apply(upStationId);
        Station downStation = findStationFunction.apply(downStationId);

        Line line = new Line(name, color, fare);
        line.add(createSection(upStation, downStation));

        return line;
    }

    private Section createSection(Station upStation, Station downStation) {
        return Section.builder()
                .upStation(upStation)
                .downStation(downStation)
                .distance(distance)
                .duration(duration)
                .build();
    }

}
