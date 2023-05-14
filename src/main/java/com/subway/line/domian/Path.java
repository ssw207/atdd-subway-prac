package com.subway.line.domian;

import com.subway.station.domain.Station;
import lombok.Builder;

import java.util.List;

@Builder
public record Path(int distance, int duration, int fare, List<Station> stations) {

    public static Path of(int distance, int duration, int fare, List<Station> stations) {
        return Path.builder()
                .distance(distance)
                .duration(duration)
                .fare(fare)
                .stations(stations)
                .build();
    }
}
