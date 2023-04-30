package com.subway.favorite.service;

import com.subway.favorite.domain.Favorite;
import com.subway.favorite.dto.FavoriteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public Favorite save(FavoriteRequest favoriteRequest) {
        return favoriteRepository.save(favoriteRequest.toEntity());
    }
}
