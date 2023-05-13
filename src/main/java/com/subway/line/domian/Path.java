package com.subway.line.domian;

import com.subway.station.domain.Station;

import java.util.List;

public record Path(int distance, int duration, List<Station> stations) {

    public static Path of(int distance, int duration, List<Station> stations) {
        return new Path(distance, duration, stations);
    }
}
