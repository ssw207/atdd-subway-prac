package com.subway.subway.station.domain;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;
    private String name;

    public Station(String name) {
        this.name = name;
    }

    public Station(Long id) {
        this.id = id;
    }

    public static Station of(long id) {
        return new Station(id);
    }

    public boolean isSameId(Long stationId) {
        return this.id.equals(stationId);
    }
}
