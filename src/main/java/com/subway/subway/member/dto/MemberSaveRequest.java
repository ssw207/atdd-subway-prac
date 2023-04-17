package com.subway.subway.member.dto;

import com.subway.subway.member.domain.Member;

public record MemberSaveRequest(String email, String password) {

    public static MemberSaveRequest of(String email, String password) {
        return new MemberSaveRequest(email, password);
    }

    public Member toEntity() {
        return Member.of(email, password);
    }
}
