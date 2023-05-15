package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FareTest {

    @Test
    void 요금계산_기본() {
        FareRequestDto fareRequestDto = FareRequestDto.builder()
                .distance(59)
                .lineFare(6)
                .build();

        Fare fare = new Fare();
        assertThat(fare.calculate(fareRequestDto)).isEqualTo(2250 + 6);
    }
}