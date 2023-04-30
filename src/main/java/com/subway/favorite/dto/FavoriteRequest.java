package com.subway.favorite.dto;

public record FavoriteRequest(long source, long target) {

    public static FavoriteRequest of(Long source, Long target) {
        return new FavoriteRequest(source, target);
    }
}
