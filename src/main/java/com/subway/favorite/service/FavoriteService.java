package com.subway.favorite.service;

import com.subway.favorite.domain.Favorite;
import com.subway.favorite.dto.FavoriteRequest;
import com.subway.station.domain.Station;
import com.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Favorite> findByMemberId(Long id) {
        return favoriteRepository.findAllByMemberId(id);
    }
}
