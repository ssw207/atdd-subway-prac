package com.subway.member.service;

import com.subway.member.domain.JwtTokenProvider;
import com.subway.member.domain.Member;
import com.subway.member.dto.AuthMember;
import com.subway.member.dto.GithubProfileResponse;
import com.subway.member.dto.GithubTokenRequest;
import com.subway.member.dto.JwtTokenRequest;
import com.subway.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final GithubClientService githubClientService;

    public String createAccessToken(JwtTokenRequest request) {
        Member member = memberService.findByEmail(request.email(), request.password());
        return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
    }

    public String createAccessToken(GithubTokenRequest request) {
        // 깃허브로 부터 엑세스 토큰 획득
        String accessTokenFromGithub = githubClientService.getAccessTokenFromGithub(request.code());

        // 엑세스 토큰으로 깃허브에서 회원정보를 받아온다
        GithubProfileResponse githubProfileFromGithub = githubClientService.getGithubProfileFromGithub(accessTokenFromGithub);

        // 회원 정보 조회, 없으면 저장
        Member member = memberRepository.findByEmail(githubProfileFromGithub.email())
                .orElseGet(() -> memberRepository.save(Member.of(githubProfileFromGithub.email(), "", 0)));

        // 토큰 생성
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
