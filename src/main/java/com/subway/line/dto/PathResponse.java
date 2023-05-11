package com.subway.line.dto;

import com.subway.line.domian.Path;
import com.subway.station.dto.StationResponse;

import java.util.List;

public record PathResponse(List<StationResponse> stations, int distance, int duration) {

    public static PathResponse of(Path path) {
//        return new PathResponse(
//                StationResponse.of(path.stations()),
//                path.distance());
        throw new UnsupportedOperationException(); // TODO 구현 필요
    }
}
