package com.subway.line.domian;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FareDistanceTest {

    @Test
    void 요금계산_기본() {
        FareDistanceBasic basic = new FareDistanceBasic();
        assertThat(basic.isEnd()).isFalse();

        FareDistance end = basic.calculate();
        assertThat(end.isEnd()).isTrue();
        assertThat(end.getFare()).isEqualTo(1250);
    }

    @Test
    void 요금계산_1구간() {
        FareDistanceLevelOne levelOne = new FareDistanceLevelOne(1, 1250);
        assertThat(levelOne.isEnd()).isFalse();

        FareDistance end = levelOne.calculate();
        assertThat(end.isEnd()).isTrue();
        assertThat(end.getFare()).isEqualTo(1350);
    }
}