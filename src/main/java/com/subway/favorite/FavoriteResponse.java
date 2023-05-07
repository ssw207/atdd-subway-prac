package com.subway.favorite;

import com.subway.favorite.domain.Favorite;
import com.subway.station.dto.StationResponse;

public record FavoriteResponse(Long id, StationResponse source, StationResponse target) {

    public static FavoriteResponse of(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getId(),
                StationResponse.of(favorite.getSource()),
                StationResponse.of(favorite.getTarget()));
    }
}
