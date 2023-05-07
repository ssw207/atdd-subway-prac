package com.subway.favorite.service;

import com.subway.favorite.domain.Favorite;
import com.subway.favorite.dto.FavoriteRequest;
import com.subway.station.domain.Station;
import com.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final StationRepository stationRepository;

    public Favorite save(FavoriteRequest favoriteRequest, long memberId) {
        Station source = findStation(favoriteRequest.source());
        Station target = findStation(favoriteRequest.target());
        return favoriteRepository.save(Favorite.of(source, target, memberId));
    }

    private Station findStation(long favoriteRequest) {
        return stationRepository.findById(favoriteRequest)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Favorite findByMemberId(Long id) {
        return favoriteRepository.findByMemberId(id)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기를 조회할 수 없습니다. " + id));
    }
}
