package com.subway.subway.member;

import com.subway.subway.member.dto.MemberCreateRequest;

public class MemberFixture {
    public static MemberCreateRequest createMemberFixture() {
        return MemberCreateRequest.of("email", "pw");
    }
}
