package com.subway.line.domian;

import com.subway.line.domian.fare.Fare;
import com.subway.line.dto.FareRequestDto;
import com.subway.station.domain.Station;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Path {

    private final int distance;
    private final int duration;
    private final List<Station> stations;
    private final int fare;

    @Builder
    public Path(Sections sections, List<Station> stations) {
        this.distance = sections.getTotalDistance();
        this.duration = sections.getTotalDuration();
        this.stations = stations;
        this.fare = calculateFare(createFareRequestDto(sections));
    }

    private FareRequestDto createFareRequestDto(Sections sections) {
        return FareRequestDto.builder()
                .lineFare(sections.getTotalLineFare())
                .distance(distance)
                .build();
    }

    private int calculateFare(FareRequestDto fareRequestDto) {
        return new Fare().calculate(fareRequestDto);
    }

}
