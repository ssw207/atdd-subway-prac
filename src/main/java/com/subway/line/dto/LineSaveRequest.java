package com.subway.line.dto;

import com.subway.line.domian.Line;
import com.subway.line.domian.Section;
import com.subway.station.domain.Station;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalTime;
import java.util.function.LongFunction;

@Builder
public record LineSaveRequest(
        @NotNull String name,
        String color,
        @NotNull Long upStationId,
        @NotNull Long downStationId,
        int duration,
        int distance,
        int fare,
        LocalTime startTime,
        LocalTime endTime,
        int term) {

    public Line toEntity(LongFunction<Station> findStationFunction) {

        // TODO 매핑 라이브러리로 변경
        Line line = Line.builder()
                .name(name)
                .color(color)
                .fare(fare)
                .startTime(startTime)
                .endTime(endTime)
                .term(term)
                .build();

        line.add(createSection(findStationFunction));

        return line;
    }

    private Section createSection(LongFunction<Station> findStationFunction) {
        return Section.builder()
                .upStation(findStationFunction.apply(upStationId))
                .downStation(findStationFunction.apply(downStationId))
                .distance(distance)
                .duration(duration)
                .build();
    }

}
