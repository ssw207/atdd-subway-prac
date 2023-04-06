package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotAddSectionException;
import com.subway.subway.common.exception.CanNotRemoveSectionException;
import com.subway.subway.station.domain.Station;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static jakarta.persistence.CascadeType.ALL;

@Slf4j
@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = ALL, orphanRemoval = true)
    private List<Section> values = new ArrayList<>();

    @Transient //Embeddable 이 붙어있으면 entity 필드로 판단하므로 단순 객체로 사용하려면 이 옵션을 붙여야한다
    private List<Station> stations = new ArrayList<>();

    public void add(Section section) {
        validateAddSection(section);
        adjustSectionIfAddMiddleSection(section);
        values.add(section);
        clearStationsCache();
    }

    private void validateAddSection(Section section) {
        List<Station> cachedStations = getCachedStations();

        if (section.isSavedSection(cachedStations)) {
            throw new CanNotAddSectionException();
        }

        if (!values.isEmpty() && section.isNotConnected(cachedStations)) {
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

    private List<Station> getCachedStations() {
        if (values.isEmpty()) {
            return new ArrayList<>();
        }

        if (stations.isEmpty()) {
            cacheStations();
        }

        return stations;
    }

    public List<Station> getStations() {
        return List.copyOf(getCachedStations());
    }

    private void clearStationsCache() {
        stations.clear();
    }

    private void cacheStations() {
        Section firstSection = getFirstSection();
        stations.add(firstSection.getUpStation());
        stations.add(firstSection.getDownStation());
        addDownStationsBySectionOrder(stations, firstSection);
    }

    private void addDownStationsBySectionOrder(List<Station> stations, Section firstSection) {
        Section startSection = firstSection;

        while (true) {
            Optional<Section> nextSection = getNextSection(startSection);
            if (nextSection.isEmpty()) {
                break;
            }

            startSection = nextSection.get();
            stations.add(startSection.getDownStation());
        }
    }

    private Section getFirstSection() {
        return values.stream()
                .filter(this::isFirstSection)
                .findAny()
                .orElseThrow();
    }

    private boolean isFirstSection(Section section) {
        return values.stream()
                .noneMatch(section::isNotFirstSection);
    }

    private Optional<Section> getNextSection(Section currentSection) {
        return values.stream()
                .filter(s -> s.isNextSection(currentSection))
                .findAny();
    }

    public int size() {
        return values.size();
    }

    public Section get(int index) {
        return values.get(index);
    }

    public void remove(Long stationId) {
        if (values.size() <= 1) {
            throw new CanNotRemoveSectionException();
        }

        // 하행역
        if (findLastStation().isSameId(stationId)) {
            values.remove(findSectionByDownStation(stationId));
            return;
        }

        Section removeTarget = findSectionByUpStation(stationId);
        values.remove(removeTarget);

        // 상행역
        if (findFirstStation().isSameId(stationId)) {
            return;
        }

        // 중간
        addDistanceToBeforeSection(removeTarget);
    }

    private void addDistanceToBeforeSection(Section removeTarget) {
        findSectionByDownStation(removeTarget.getUpStationId())
                .addDistance(removeTarget.getDistance());
    }

    private Station findLastStation() {
        List<Station> notEmptyCachedStations = getNotEmptyCachedStations();
        return notEmptyCachedStations.get(notEmptyCachedStations.size() - 1);
    }

    private Station findFirstStation() {
        return getNotEmptyCachedStations().get(0);
    }

    private List<Station> getNotEmptyCachedStations() {
        List<Station> cachedStations = getCachedStations();

        if (cachedStations.isEmpty()) {
            throw new IllegalStateException("조회할 역이 없습니다.");
        }

        return cachedStations;
    }

    private Section findSectionByUpStation(Long stationId) {
        return findSection(Section::getUpStation, stationId);
    }

    private Section findSectionByDownStation(Long stationId) {
        return findSection(Section::getDownStation, stationId);
    }

    private Section findSection(Function<Section, Station> getStationFunction, Long stationId) {
        return values.stream()
                .filter(s -> getStationFunction.apply(s).isSameId(stationId))
                .findAny()
                .orElseThrow(CanNotRemoveSectionException::new);
    }
}
