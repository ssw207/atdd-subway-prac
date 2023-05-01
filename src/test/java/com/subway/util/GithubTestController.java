package com.subway.util;

import com.subway.member.dto.GithubAccessTokenResponse;
import com.subway.member.dto.GithubProfileResponse;
import com.subway.member.dto.GithubTokenRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/github")
@RestController
public class GithubTestController {

    @PostMapping("login/access-token")
    public GithubAccessTokenResponse getGithubAccessToken(@RequestBody GithubTokenRequest request) {
        String accessToken = GithubTestUserResponse.findByCode(request.code()).getAccessToken();
        return new GithubAccessTokenResponse(accessToken, "", "", "");
    }

    @GetMapping("user")
    public GithubProfileResponse getGithubUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        log.debug("authorization : {}", authorization);
        String accessToken = authorization.split(" ")[1];
        String email = GithubTestUserResponse.findByToken(accessToken).getEmail();
        return GithubProfileResponse.of(email);
    }
}

