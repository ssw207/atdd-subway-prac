package com.subway.member;

import com.subway.member.dto.MemberSaveRequest;

public class MemberFixture {
    public static MemberSaveRequest createMemberFixture() {
        return MemberSaveRequest.of("email", "pw");
    }
}
