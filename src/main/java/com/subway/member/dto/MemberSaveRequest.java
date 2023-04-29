package com.subway.member.dto;

import com.subway.member.domain.Member;

public record MemberSaveRequest(String email, String password, int age) {

    public static MemberSaveRequest of(String email, String password, int age) {
        return new MemberSaveRequest(email, password, age);
    }

    public Member toEntity() {
        return Member.of(email, password, age);
    }
}
