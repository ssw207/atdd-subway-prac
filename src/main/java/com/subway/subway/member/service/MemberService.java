package com.subway.subway.member.service;

import com.subway.subway.member.dto.MemberSaveRequest;
import com.subway.subway.member.repository.MemberRepository;
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
}
