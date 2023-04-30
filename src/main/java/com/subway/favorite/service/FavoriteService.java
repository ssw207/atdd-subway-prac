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
        Station source = stationRepository.findById(favoriteRequest.source()).orElseThrow(IllegalArgumentException::new);
        Station target = stationRepository.findById(favoriteRequest.target()).orElseThrow(IllegalArgumentException::new);
        return favoriteRepository.save(Favorite.of(source, target, memberId));
    }
}
