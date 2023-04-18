package com.subway.line.domian;

import com.subway.station.domain.Station;

import java.util.List;

public record Path(int distance, List<Station> stations) {

    public static Path of(int distance, List<Station> stations) {
        return new Path(distance, stations);
    }
}
