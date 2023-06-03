package com.subway.line.dto;

import com.subway.line.domian.Path;
import com.subway.station.dto.StationResponse;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

@Builder
public record PathResponse(List<StationResponse> stations, int distance, int duration, int fare, LocalTime arriveTime) {

    public static PathResponse of(Path path, int totalFare) {
//        return PathResponse.builder()
//                .stations(StationResponse.of(path.getStations()))
//                .distance(path.getDistance())
//                .duration(path.getDuration())
//                .fare(totalFare)
//                .build();
        throw new UnsupportedOperationException(); // TODO 구현 필요
    }
}
