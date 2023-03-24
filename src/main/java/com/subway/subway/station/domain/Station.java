package com.subway.subway.station.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;
    private String name;

    public Station(String name) {
        this.name = name;
    }
}
