package com.subway.favorite.service;

import com.subway.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByMemberId(Long id);
}
