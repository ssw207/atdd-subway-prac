package com.subway.member.dto;

import lombok.Builder;

@Builder
public record AuthMember(long id, String email, int age, String role) {

    public static AuthMember of(long id, String email, int age, String role) {
        return new AuthMember(id, email, age, role);
    }

    public static AuthMember empty() {
        return AuthMember.builder().build();
    }
}
