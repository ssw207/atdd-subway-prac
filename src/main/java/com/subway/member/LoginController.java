package com.subway.member;

import com.subway.member.dto.JwtTokenRequest;
import com.subway.member.dto.JwtTokenResponse;
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
    public JwtTokenResponse createJwtToken(@RequestBody JwtTokenRequest request) {
        String token = tokenService.createToken(request);
        return JwtTokenResponse.of(token);
    }
}
