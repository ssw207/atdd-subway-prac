package com.subway.line.dto;

import com.subway.line.domian.Line;
import com.subway.line.domian.Section;
import com.subway.station.domain.Station;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.LongFunction;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LineSaveRequest {

    @NotEmpty
    private String name;
    private String color;
    private long upStationId;
    private long downStationId;
    private int duration;
    private int distance;
    private int fare;

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
