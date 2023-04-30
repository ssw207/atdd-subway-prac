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

    @Column(nullable = false)
    private long memberId; // 즐겨찾기에서 회원정보를 조회하는 경우는 없으므로 연관관계를 맺지 않는다.
}
