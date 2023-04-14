package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;

import java.util.List;

public record Path(int distance, List<Station> stations) {
}
