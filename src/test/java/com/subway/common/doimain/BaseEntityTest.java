package com.subway.common.doimain;

import com.subway.member.domain.Member;
import com.subway.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class BaseEntityTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 등록일_수정일이_자동으로_등록된다() {
        Member save = memberRepository.save(Member.of("email", "pw", 20));
        assertThat(save.getRegYmdt()).isNotNull();
        assertThat(save.getModYmdt()).isNotNull();
    }
}