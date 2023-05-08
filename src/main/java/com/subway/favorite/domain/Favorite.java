package com.subway.favorite.domain;

import com.subway.station.domain.Station;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 프록시는 상속기반으로 동작하므로 접근제한자를 protected로 설정한다
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @JoinColumn(name = "source_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Station source;

    @JoinColumn(name = "target_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Station target;

    @Column(nullable = false)
    private Long memberId; // 즐겨찾기에서 회원정보를 조회하는 경우는 없으므로 연관관계를 맺지 않는다.

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

    public boolean isMyFavorite(Long memberId) {
        return this.memberId.equals(memberId);
    }
}
