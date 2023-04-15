package com.subway.subway.line.dto;

import com.subway.subway.line.domian.Path;
import com.subway.subway.station.dto.StationResponse;

import java.util.List;

public record PathResponse(List<StationResponse> stations, int distance) {

    public static PathResponse of(Path path) {
        return new PathResponse(
                StationResponse.of(path.stations()),
                path.distance());
    }
}
