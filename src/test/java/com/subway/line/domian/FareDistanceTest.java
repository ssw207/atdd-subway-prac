package com.subway.line.domian;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FareDistanceTest {

    @Test
    void 요금계산_기본() {
        FareDistance fare = new FareDistanceBasic();
        assertThat(fare.calculate()).isEqualTo(1250);
    }
}