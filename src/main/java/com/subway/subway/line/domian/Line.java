package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    private String name;
    private String color;

    @Embedded
    private Sections sections = new Sections();

    private int fare;

    public Line(String name, String color, int fare) {
        this.name = name;
        this.color = color;
        this.fare = fare;
    }

    public void add(Section section) {
        sections.add(section);
        section.changeLine(this);
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
}
