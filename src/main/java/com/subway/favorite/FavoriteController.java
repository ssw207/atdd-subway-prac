package com.subway.favorite;

import com.subway.favorite.domain.Favorite;
import com.subway.favorite.dto.FavoriteRequest;
import com.subway.favorite.service.FavoriteService;
import com.subway.member.dto.AuthMember;
import com.subway.member.resolver.AuthMemberPrincipal;
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
    public ResponseEntity<Void> saveFavorite(@RequestBody FavoriteRequest favoriteRequest, @AuthMemberPrincipal AuthMember authMember) {
        Favorite favorite = favoriteService.save(favoriteRequest, authMember.id());
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).build();
    }
}
