package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

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
}

