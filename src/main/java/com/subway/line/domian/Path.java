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
    public Path(int distance, int duration, List<Station> stations) {
        this.distance = distance;
        this.duration = duration;
        this.stations = stations;
        this.fare = calculateFare(distance);
    }

    private int calculateFare(int distance) {
        return new Fare().calculate(new FareRequestDto(distance));
    }
}
