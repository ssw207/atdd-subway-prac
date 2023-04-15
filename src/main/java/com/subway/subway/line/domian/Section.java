package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Slf4j
@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "up_statioin_id")
    private Station upStation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "down_staion_id")
    private Station downStation;

    private int duration;

    private int distance;

    public void changeLine(Line line) {
        this.line = line;
    }

    public boolean isSavedSection(List<Station> stations) {
        return stations.contains(upStation) && stations.contains(downStation);
    }

    public boolean isNotConnected(List<Station> stations) {
        return !stations.contains(upStation) && !stations.contains(downStation);
    }

    public boolean isSameUpStation(Station upStation) {
        return this.upStation.equals(upStation);
    }

    public int minusDistance(int distance) {
        return this.distance - distance;
    }

    public boolean isDistanceLongerThen(Section savedSection) {
        return distance > savedSection.getDistance();
    }

    public boolean isSameDownStation(Station station) {
        return downStation.equals(station);
    }

    public boolean isNotFirstSection(Section section) {
        return upStation.equals(section.getDownStation());
    }

    public boolean isNextSection(Section currentSection) {
        return currentSection.isSameDownStation(upStation);
    }

    public void addDistance(int distance) {
        this.distance += distance;
    }

    public Long getUpStationId() {
        return upStation.getId();
    }

    public void changeDownStation(Station downStation) {
        this.downStation = downStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section section)) return false;
        return getId() != null && Objects.equals(getId(), section.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

