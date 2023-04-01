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
        validateAddSection(section);
        adjustSectionIfAddMiddleSection(section);
        values.add(section);
    }

    private void validateAddSection(Section section) {
        List<Station> stations = getStations();

        if (section.isSavedSection(stations)) {
            throw new CanNotAddSectionException();
        }

        if (!values.isEmpty() && section.isNotConnected(stations)) {
            throw new CanNotAddSectionException();
        }
    }

    private void adjustSectionIfAddMiddleSection(Section section) {
        findMiddleSection(section).ifPresent(savedMiddleSection -> {
            validateAddMiddleSection(section, savedMiddleSection);
            replaceSection(savedMiddleSection, createFixedMiddleSection(section, savedMiddleSection));
        });
    }

    private void validateAddMiddleSection(Section section, Section savedSection) {
        if (section.isDistanceLongerThen(savedSection)) {
            throw new CanNotAddSectionException();
        }
    }

    private void replaceSection(Section savedSection, Section newSection) {
        values.remove(savedSection);
        values.add(newSection);
    }

    private Section createFixedMiddleSection(Section section, Section savedSection) {
        return Section.builder()
                .line(savedSection.getLine())
                .upStation(section.getDownStation())
                .downStation(savedSection.getDownStation())
                .distance(savedSection.minusDistance(section.getDistance()))
                .build();
    }

    private Optional<Section> findMiddleSection(Section section) {
        return values.stream()
                .filter(s -> section.isSameUpStation(s.getUpStation()))
                .findAny();
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
