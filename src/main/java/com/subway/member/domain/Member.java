package com.subway.member.domain;

import com.subway.common.doimain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member of(String email, String password) {
        return of(email, password, Role.USER);
    }

    public static Member of(String email, String password, Role role) {
        return new Member(email, password, role);
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
}
