package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotAddSectionException;
import com.subway.subway.station.domain.Station;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jakarta.persistence.CascadeType.ALL;

@Slf4j
@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = ALL, orphanRemoval = true)
    private List<Section> values = new ArrayList<>();

    public void add(Section section) {

        List<Station> stations = values.stream()
                .flatMap(s -> Stream.of(s.getUpStation(), s.getDownStation()))
                .toList();

        if (section.isSavedSection(stations)) {
            throw new CanNotAddSectionException();
        }

        if (!values.isEmpty() && section.isNotConnected(stations)) {
            throw new CanNotAddSectionException();
        }

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
