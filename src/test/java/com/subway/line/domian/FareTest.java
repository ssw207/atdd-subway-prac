package com.subway.line.domian;

import com.subway.line.domian.fare.Fare;
import com.subway.line.dto.FareRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FareTest {

    @Test
    void 요금계산_기본() {
        FareRequestDto fareRequestDto = FareRequestDto.ofDistancePolicy(59);
        Fare fare = new Fare();
        assertThat(fare.calculate(fareRequestDto)).isEqualTo(2250);
    }
}