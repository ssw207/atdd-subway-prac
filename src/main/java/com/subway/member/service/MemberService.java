package com.subway.member.service;

import com.subway.member.domain.Member;
import com.subway.member.dto.MemberSaveRequest;
import com.subway.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequest request) {
        return memberRepository.save(request.toEntity()).getId();
    }

    public Member findByEmail(String email, String password) {
        Member member = findByEmail(email);
        if (!member.isValidPassword(password)) {
            throw new IllegalArgumentException();
        }

        return member;
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
    }
}
