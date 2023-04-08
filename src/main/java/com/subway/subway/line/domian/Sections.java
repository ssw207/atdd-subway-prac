package com.subway.subway.line.domian;

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

    public static final SectionActionFactory ACTION_FACTORY = new SectionActionFactory();

    @OneToMany(mappedBy = "line", cascade = ALL, orphanRemoval = true)
    private List<Section> values = new ArrayList<>();

    @Transient //Embeddable 이 붙어있으면 entity 필드로 판단하므로 단순 객체로 사용하려면 이 옵션을 붙여야한다
    private List<Station> stations = new ArrayList<>();

    public void add(Section section) {
        SectionAddAction action = ACTION_FACTORY.createAddAction(this, section);
        action.add();
    }

    public void forceAdd(Section section) {
        values.add(section);
        stationCacheClear();
    }

    public Optional<Section> findMiddleSection(Section section) {
        return findSection(Section::getUpStation, section.getUpStationId());
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

    public boolean isFirstStation(Long stationId) {
        return findFirstStation().isSameId(stationId); // getFirstSection 을 사용하면 안될까?
    }

    public boolean isLastStation(Long stationId) {
        return findLastStation().isSameId(stationId);
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

    public Section findSectionByUpStation(Long stationId) {
        return findSection(Section::getUpStation, stationId)
                .orElseThrow(CanNotRemoveSectionException::new);
    }

    public Section findSectionByDownStation(Long stationId) {
        return findSection(Section::getDownStation, stationId)
                .orElseThrow(CanNotRemoveSectionException::new);
    }

    private Optional<Section> findSection(Function<Section, Station> getStationFunction, Long stationId) {
        return values.stream()
                .filter(s -> getStationFunction.apply(s).isSameId(stationId))
                .findAny();
    }

    public void remove(Long stationId) {
        SectionRemoveAction action = ACTION_FACTORY.createRemoveAction(this, stationId);
        action.validate();
        action.remove();
        stationCacheClear();
    }

    /**
     * SectionRemoveAction에서 Sections의 요소를 삭제하기 위해 호출하는 메서드.
     * 삭제 관련 비즈니스 로직을 타지 않고 단순 삭제만 처리한다.
     * 비즈니스 목적으로 삭제를 호출시 사용해선 안된다.
     * 비즈니스 목적으로 삭제호출시 문제가 발생할수 있으므로 접근제한자를 최대한 제한한다
     * 이 메서드를 외부에 공개하는게 좋은지 고민이 필요하다
     */
    void remove(Section section) {
        values.remove(section);
        stationCacheClear();
    }

    private void stationCacheClear() {
        if (stations.isEmpty()) {
            return;
        }

        stations.clear();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
