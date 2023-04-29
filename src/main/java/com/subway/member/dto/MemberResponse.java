package com.subway.member.dto;

import com.subway.member.domain.Member;

public record MemberResponse(Long id, String email, int age) {

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getAge());
    }
}
