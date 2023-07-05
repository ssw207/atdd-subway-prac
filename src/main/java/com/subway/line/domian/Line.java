package com.subway.line.domian;

import com.subway.station.domain.Station;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;
    private String name;
    private String color;
    private int fare;

    @Embedded
    private Sections sections; // 빌더를 통해 생성하면 초기값이 무시되는 이슈있음

    @Embedded
    private LineTime lineTime;

    @Builder
    public Line(Long id, String name, String color, LocalTime startTime, LocalTime endTime, int term, Sections sections, int fare) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.lineTime = createLineTime(startTime, endTime, term);
        this.sections = createSectionsIfNull(sections);
        this.fare = fare;
    }

    private LineTime createLineTime(LocalTime startTime, LocalTime endTime, int term) {
        return new LineTime(startTime, endTime, term);
    }

    private Sections createSectionsIfNull(Sections sections) {
        return (sections == null) ? new Sections() : sections;
    }

    public void add(Section section) {
        section.changeLine(this);
        sections.add(section);
    }

    public List<Station> getStations() {
        return sections.getStations();
    }

    public void update(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void removeSection(long stationId) {
        sections.remove(stationId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line line)) return false;
        return getId() != null && Objects.equals(getId(), line.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public LocalTime getStartTime() {
        return lineTime.getStartTime();
    }

    public LocalTime getEndTime() {
        return lineTime.getEndTime();
    }

    public int getTerm() {
        return lineTime.getTerm();
    }
}
