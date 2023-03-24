package com.subway.subway.station.service;

import com.subway.subway.station.domain.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StationSaveRequest {

    private String name;

    public Station toEntity() {
        return new Station(name);
    }
}
