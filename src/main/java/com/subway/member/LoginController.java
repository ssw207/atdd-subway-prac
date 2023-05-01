package com.subway.member;

import com.subway.member.dto.GithubTokenRequest;
import com.subway.member.dto.JwtTokenRequest;
import com.subway.member.dto.TokenResponse;
import com.subway.member.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final TokenService tokenService;

    @PostMapping("token")
    public TokenResponse createJwtToken(@RequestBody JwtTokenRequest request) {
        return TokenResponse.of(tokenService.createAccessToken(request));
    }

    @PostMapping("github")
    public TokenResponse createGithubToken(@RequestBody GithubTokenRequest request) { // TODO 로그인 end point를 하나로 합치느게 좋을까?
        return TokenResponse.of(tokenService.createAccessToken(request));
    }
}
