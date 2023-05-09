package com.subway.favorite;

import com.subway.favorite.domain.Favorite;
import com.subway.favorite.dto.FavoriteRequest;
import com.subway.favorite.dto.FavoriteResponse;
import com.subway.favorite.service.FavoriteService;
import com.subway.member.dto.AuthMember;
import com.subway.member.resolver.AuthMemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public List<FavoriteResponse> findFavorite(@AuthMemberPrincipal AuthMember authMember) {
        List<Favorite> favorites = favoriteService.findByMemberId(authMember.id());
        return FavoriteResponse.of(favorites);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@AuthMemberPrincipal AuthMember authMember, @PathVariable Long id) {
        favoriteService.deleteMyFavoriteById(authMember.id(), id);
        return ResponseEntity.noContent().build();
    }
}
