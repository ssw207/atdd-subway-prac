package com.subway.line.dto;

import com.subway.line.domian.Path;
import com.subway.station.dto.StationResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record PathResponse(List<StationResponse> stations, int distance, int duration, int fare) {

    public static PathResponse of(Path path) {
        return PathResponse.builder()
                .stations(StationResponse.of(path.stations()))
                .distance(path.distance())
                .duration(path.duration())
                .fare(path.fare())
                .build();
    }
}
