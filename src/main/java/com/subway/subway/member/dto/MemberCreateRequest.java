package com.subway.subway.member.dto;

public record MemberCreateRequest(String email, String password) {

    public static MemberCreateRequest of(String email, String password) {
        return new MemberCreateRequest(email, password);
    }
}
