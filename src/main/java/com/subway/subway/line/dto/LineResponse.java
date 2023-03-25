package com.subway.subway.line.dto;

import com.subway.subway.line.domian.Line;
import com.subway.subway.station.dto.StationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineResponse {

    private String name;
    private int fare;
    private List<StationResponse> stations;

    public static LineResponse of(Line line) {
        return new LineResponse(
                line.getName(),
                line.getFare(),
                StationResponse.of(line.getStations()));
    }
}
