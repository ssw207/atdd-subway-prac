package com.subway.station.dto;

import com.subway.station.domain.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StationResponse {

    private Long id;
    private String name;

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
