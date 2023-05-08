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

    public void deleteMyFavoriteById(Long memberId, Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 즐겨찾기 입니다. id:" + favoriteId));

        if (!favorite.isMyFavorite(memberId)) {
            throw new IllegalArgumentException("자신의 즐겨찾기만 삭제 가능합니다 즐겨찾기 회원 아이디");
        }

        favoriteRepository.delete(favorite);
    }
}
