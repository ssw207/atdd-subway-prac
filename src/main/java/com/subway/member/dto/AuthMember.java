package com.subway.member.dto;

public record AuthMember(long id, String email, int age, String role) {

    public static AuthMember of(long id, String email, int age, String role) {
        return new AuthMember(id, email, age, role);
    }
}
