package com.subway.favorite.domain;

import com.subway.station.domain.Station;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @JoinColumn(name = "source_id")
    @ManyToOne
    private Station source;

    @JoinColumn(name = "target_id")
    @ManyToOne
    private Station target;

    @Column(nullable = false)
    private long memberId; // 즐겨찾기에서 회원정보를 조회하는 경우는 없으므로 연관관계를 맺지 않는다.

    @Builder
    public Favorite(Station source, Station target, long memberId) {
        this.source = source;
        this.target = target;
        this.memberId = memberId;
    }

    public static Favorite of(Station source, Station target, long memberId) {
        return Favorite.builder()
                .source(source)
                .target(target)
                .memberId(memberId)
                .build();
    }
}
