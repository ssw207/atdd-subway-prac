package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotAddSectionException;
import com.subway.subway.station.domain.Station;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.ALL;

@Slf4j
@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = ALL, orphanRemoval = true)
    private List<Section> values = new ArrayList<>();

    public void add(Section section) {

        List<Station> stations = getStations();

        if (section.isSavedSection(stations)) {
            throw new CanNotAddSectionException();
        }

        if (!values.isEmpty() && section.isNotConnected(stations)) {
            throw new CanNotAddSectionException();
        }

        addSection(section);
    }

    private void addSection(Section section) {
        Optional<Section> middleSectionTarget = values.stream()
                .filter(s -> section.isSameUpStation(s.getUpStation()))
                .findAny();

        if (middleSectionTarget.isPresent()) {
            Section savedSection = middleSectionTarget.get();

            if (section.isDistanceLongerThen(savedSection)) {
                throw new CanNotAddSectionException();
            }

            values.remove(savedSection);
            values.add(section);

            int newSectionDistance = savedSection.minusDistance(section.getDistance());
            Section newSection = Section.builder()
                    .line(savedSection.getLine())
                    .upStation(section.getDownStation())
                    .downStation(savedSection.getDownStation())
                    .distance(newSectionDistance)
                    .build();

            values.add(newSection);

        } else {
            values.add(section);
        }
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

    public int size() {
        return values.size();
    }

    public Section get(int index) {
        return values.get(index);
    }
}
