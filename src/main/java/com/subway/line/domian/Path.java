package com.subway.line.domian;

import com.subway.station.domain.Station;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class Path {

    private final Sections sections;
    private final List<Station> stations;

    @Builder
    public Path(Sections sections, List<Station> stations) {
        this.sections = sections;
        this.stations = stations;
    }

    public int getTotalLineFare() {
        return sections.getTotalLineFare();
    }

    public int getDistance() {
        return sections.getTotalDistance();
    }

    public int getDuration() {
        return sections.getTotalDuration();
    }

    public LocalTime getArriveTime() {
        return null; // TODO 구현필요
    }
}
