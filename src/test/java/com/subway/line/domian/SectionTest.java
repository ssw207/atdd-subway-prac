package com.subway.line.domian;

import com.subway.line.LineFixture;
import com.subway.line.SectionFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class SectionTest {

    @Test
    void 도착시간_계산() {
        //given
        LocalTime 이전역_도착시간 = LocalTime.now();
        LocalTime 현재역_도착시간 = 이전역_도착시간.minusMinutes(5);

        Section section1 = SectionFixture.createSection(0L, 1L, 이전역_도착시간);
        Section section2 = SectionFixture.createSection(1L, 2L);

        Line line = LineFixture.createLine(10);
        line.add(section1);
        line.add(section2);

        //when
        section2.initArriveTime(section1.getArriveTime());

        //then
        assertThat(section2.getArriveTime()).isEqualTo(현재역_도착시간);
    }
}