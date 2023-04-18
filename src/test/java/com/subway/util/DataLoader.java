package com.subway.util;

import com.subway.member.domain.Member;
import com.subway.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void loadData() {
        log.info(">> 초기 회원 데이터 입력 시작");
        memberRepository.save(Member.of("admin@email.com", "password"));
        memberRepository.save(Member.of("member@email.com", "password"));
    }
}
