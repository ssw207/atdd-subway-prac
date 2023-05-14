package com.subway.line.domian;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FareTest {

    @Test
    void 요금계산_기본() {
        int result = new Fare().calculate(new FareRequestDto(10));
        assertThat(result).isEqualTo(1250);
    }

    @Test
    void 요금계산_11km() {
        int result = new Fare().calculate(new FareRequestDto(11));
        assertThat(result).isEqualTo(1350);
    }

    @Test
    void 요금계산_16km() {
        int result = new Fare().calculate(new FareRequestDto(16));
        assertThat(result).isEqualTo(1450);
    }

    @Test
    void 요금계산_50km() {
        int result = new Fare().calculate(new FareRequestDto(50));
        assertThat(result).isEqualTo(2050);
    }

    @Test
    void 요금계산_51km() {
        int result = new Fare().calculate(new FareRequestDto(51));
        assertThat(result).isEqualTo(2150);
    }

    @Test
    void 요금계산_59km() {
        int result = new Fare().calculate(new FareRequestDto(59));
        assertThat(result).isEqualTo(2250);
    }
}