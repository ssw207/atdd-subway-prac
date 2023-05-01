package com.subway.member.service;

import com.subway.member.domain.JwtTokenProvider;
import com.subway.member.domain.Member;
import com.subway.member.dto.AuthMember;
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

    public AuthMember findAuthMemberByToken(String jwtToken) {
        if (!jwtTokenProvider.validateToken(jwtToken)) {
            throw new IllegalArgumentException("jwt token이 유효하지 않습니다.");
        }

        String email = jwtTokenProvider.getPrincipal(jwtToken);
        Member member = memberService.findByEmail(email);

        return AuthMember.of( // AuthUser는 최대한 Member의존성을 가지지 않게 설계
                member.getId(),
                member.getEmail(),
                member.getAge(),
                member.getRoleCode());
    }
}
