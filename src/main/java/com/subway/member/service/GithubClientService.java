package com.subway.member.service;

import com.subway.config.properties.GithubProperties;
import com.subway.member.dto.GithubAccessTokenRequest;
import com.subway.member.dto.GithubProfileResponse;
import com.subway.member.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GithubClientService {

    private final GithubProperties githubProperties;

    public String getAccessTokenFromGithub(String code) {
        GithubAccessTokenRequest githubAccessTokenRequest = new GithubAccessTokenRequest(
                code,
                githubProperties.getClientId(),
                githubProperties.getClientSecret()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(
                githubAccessTokenRequest, headers);
        RestTemplate restTemplate = new RestTemplate();

        String accessToken = restTemplate
                .exchange(githubProperties.getTokenUrl(), HttpMethod.POST, httpEntity, TokenResponse.class)
                .getBody()
                .accessToken();
        if (accessToken == null) {
            throw new RuntimeException();
        }
        return accessToken;
    }

    public GithubProfileResponse getGithubProfileFromGithub(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + accessToken);

        HttpEntity httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate
                    .exchange(githubProperties.getProfileUrl(), HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                    .getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException();
        }
    }
}
