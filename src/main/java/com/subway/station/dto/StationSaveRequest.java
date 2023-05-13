package com.subway.station.dto;

import com.subway.station.domain.Station;

public record StationSaveRequest(String name) {

    public Station toEntity() {
        return new Station(name);
    }
}
