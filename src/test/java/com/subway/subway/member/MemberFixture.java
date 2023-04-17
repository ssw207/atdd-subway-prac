package com.subway.subway.member;

import com.subway.subway.member.dto.MemberSaveRequest;

public class MemberFixture {
    public static MemberSaveRequest createMemberFixture() {
        return MemberSaveRequest.of("email", "pw");
    }
}
