package com.subway.favorite.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;
}
