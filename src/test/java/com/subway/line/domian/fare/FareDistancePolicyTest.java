package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FareDistancePolicyTest {

    @Test
    void 요금_계산() {
        FarePolicy policy = new FareDistancePolicy();
        int calculate = policy.calculate(new FareRequestDto(59));
        assertThat(calculate).isEqualTo(2250);
    }
}