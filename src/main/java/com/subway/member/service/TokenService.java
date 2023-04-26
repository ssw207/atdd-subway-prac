package com.subway.member.service;

import com.subway.member.domain.JwtTokenProvider;
import com.subway.member.domain.Member;
import com.subway.member.dto.JwtTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public String createAccessToken(JwtTokenRequest request) {
        Member member = memberService.findByEmail(request.email(), request.password());
        return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
    }
}
