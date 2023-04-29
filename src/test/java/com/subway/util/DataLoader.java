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

    public static final String EMAIL_ADMIN = "admin@email.com";
    public static final String EMAIL_MEMBER = "member@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    private final MemberRepository memberRepository;

    @PostConstruct
    public void loadData() {
        log.info(">> 초기 회원 데이터 입력 시작");
        memberRepository.save(Member.of(EMAIL_ADMIN, PASSWORD, AGE));
        memberRepository.save(Member.of(EMAIL_MEMBER, PASSWORD, AGE));
    }
}
