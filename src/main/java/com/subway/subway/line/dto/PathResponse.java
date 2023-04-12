package com.subway.subway.line.dto;

import com.subway.subway.station.domain.Station;

import java.util.List;

public record PathResponse(List<Station> stations, int distance) {
}
