package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    private String name;

    @Embedded
    private final Sections sections = new Sections();

    private int fare;

    public Line(String name, int fare) {
        this.name = name;
        this.fare = fare;
    }

    public void add(Section section) {
        sections.add(section);
        section.changeLine(this);
    }

    public List<Station> getStations() {
        return sections.getStations();
    }
}
