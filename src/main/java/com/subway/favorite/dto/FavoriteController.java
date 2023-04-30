package com.subway.favorite.dto;

import com.subway.favorite.domain.Favorite;
import com.subway.favorite.service.FavoriteService;
import com.subway.member.AuthUser;
import com.subway.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/favorites")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Void> saveFavorite(@RequestBody FavoriteRequest favoriteRequest, @AuthUser MemberResponse memberResponse) {
        Favorite favorite = favoriteService.save(favoriteRequest, memberResponse.id());
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).build();
    }
}
