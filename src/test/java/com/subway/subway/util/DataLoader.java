package com.subway.subway.util;

import com.subway.subway.member.domain.Member;
import com.subway.subway.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final MemberRepository memberRepository;

    public void loadData() {
        memberRepository.save(Member.of("admin@email.com", "password"));
        memberRepository.save(Member.of("member@email.com", "password"));
    }
}
