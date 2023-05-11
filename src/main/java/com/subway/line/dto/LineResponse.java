package com.subway.line.dto;

import com.subway.line.domian.Line;
import com.subway.station.dto.StationResponse;

import java.util.ArrayList;
import java.util.List;

public record LineResponse(Long id, String name, String color, int fare, List<StationResponse> stations) {

    public static LineResponse of(Line line) {
        return new LineResponse(
                line.getId(),
                line.getName(),
                line.getColor(),
                line.getFare(),
                StationResponse.of(line.getStations()));
    }

    public static List<LineResponse> of(List<Line> lines) {
        if (lines == null) {
            return new ArrayList<>();
        }

        return lines.stream()
                .map(LineResponse::of)
                .toList();
    }
}
