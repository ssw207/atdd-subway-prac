package com.subway.favorite;

import com.subway.favorite.dto.FavoriteRequest;

public class FavoriteFixture {
    public static FavoriteRequest createFavoriteFixture(Long source, Long target) {
        return FavoriteRequest.of(source, target);
    }
}
