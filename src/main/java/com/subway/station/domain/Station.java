package com.subway.station.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station station)) return false;
        return getId() != null && Objects.equals(getId(), station.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
