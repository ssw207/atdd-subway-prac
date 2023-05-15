package com.subway.line.domian.fare;

import com.subway.line.domian.fare.distance.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FareDistanceTest {

    @Test
    void 요금계산_기본() {
        FareDistance basic = new FareDistanceBasic(0);
        assertThat(basic.isEnd()).isFalse();

        FareDistance end = basic.calculate();
        assertThat(end.isEnd()).isTrue();
        assertThat(end.getFare()).isEqualTo(1250);
    }

    @Test
    void 요금계산_1구간_생성() {
        FareDistance levelOne = new FareDistanceBasic(11).calculate();
        assertThat(levelOne).isInstanceOf(FareDistanceLevelOne.class);
        assertThat(levelOne.calculate()).isInstanceOf(FareDistanceEnd.class);
    }

    @Test
    void 요금계산_1구간_5km() {
        FareDistance levelOne = new FareDistanceBasic(15).calculate();
        assertThat(levelOne.isEnd()).isFalse();

        FareDistance end = levelOne.calculate();
        assertThat(end.isEnd()).isTrue();
        assertThat(end.getFare()).isEqualTo(1350);
    }

    @Test
    void 요금계산_1구간_6km() {
        FareDistance levelOne = new FareDistanceBasic(16).calculate();
        assertThat(levelOne.isEnd()).isFalse();

        FareDistance end = levelOne.calculate();
        assertThat(end.isEnd()).isTrue();
        assertThat(end.getFare()).isEqualTo(1450);
    }

    @Test
    void 요금계산_2구간_생성() {
        FareDistance levelOne = new FareDistanceBasic(51).calculate();
        assertThat(levelOne).isInstanceOf(FareDistanceLevelOne.class);

        FareDistance levelTwo = levelOne.calculate();
        assertThat(levelTwo).isInstanceOf(FareDistanceLevelTwo.class);

        assertThat(levelTwo.calculate()).isInstanceOf(FareDistanceEnd.class);
    }

    @Test
    void 요금계산_2구간_8km() {
        FareDistance two = new FareDistanceBasic(58).calculate().calculate();
        assertThat(two.isEnd()).isFalse();

        FareDistance end = two.calculate();
        assertThat(end.isEnd()).isTrue();
        assertThat(end.getFare()).isEqualTo(2150);
    }

    @Test
    void 요금계산_2구간_9km() {
        FareDistance two = new FareDistanceBasic(59).calculate().calculate();
        assertThat(two.isEnd()).isFalse();

        FareDistance end = two.calculate();
        assertThat(end.isEnd()).isTrue();
        assertThat(end.getFare()).isEqualTo(2250);
    }
}