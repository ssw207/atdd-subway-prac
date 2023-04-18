package com.subway.line.dto;

import com.subway.line.domian.Line;
import com.subway.station.dto.StationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineResponse {

    private Long id;
    private String name;
    private String color;
    private int fare;
    private List<StationResponse> stations;

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
