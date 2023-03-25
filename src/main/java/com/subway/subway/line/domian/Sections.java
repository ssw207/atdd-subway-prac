package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.ALL;

@Slf4j
@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = ALL, orphanRemoval = true)
    private List<Section> values = new ArrayList<>();

    public void add(Section section) {
        values.add(section);
    }

    public List<Station> getStations() {
        if (values.isEmpty()) {
            return new ArrayList<>();
        }

        List<Station> stations = values.stream()
                .map(Section::getUpStation)
                .collect(Collectors.toList());

        stations.add(getLastDownStation());

        log.debug(stations.toString());

        return stations;
    }

    private Station getLastDownStation() {
        return values.get(values.size() - 1).getDownStation();
    }
}
