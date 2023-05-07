package com.subway.station.dto;

import com.subway.station.domain.Station;

import java.util.List;

public record StationResponse(Long id, String name) {
    
    public static StationResponse of(Station station) {
        return new StationResponse(station.getId(), station.getName());
    }

    public static List<StationResponse> of(List<Station> stations) {
        return stations
                .stream()
                .map(StationResponse::of)
                .toList();
    }
}
