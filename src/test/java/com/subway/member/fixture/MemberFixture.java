package com.subway.member.fixture;

import com.subway.member.dto.MemberSaveRequest;

public class MemberFixture {
    public static MemberSaveRequest createMemberFixture() {
        return MemberSaveRequest.of("email", "pw");
    }
}
