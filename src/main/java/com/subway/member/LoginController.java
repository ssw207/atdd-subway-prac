package com.subway.member;

import com.subway.member.dto.JwtTokenRequest;
import com.subway.member.service.MemberService;
import com.subway.member.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final TokenService tokenService;

    @PostMapping("token")
    public String createJwtToken(@RequestBody JwtTokenRequest request) {
        return tokenService.createToken(request);
    }
}
