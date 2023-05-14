package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FareLinePolicyTest {

    @Test
    void 노선_요금정책() {
        FareLinePolicy fareLinePolicy = new FareLinePolicy();
        FareRequestDto dto = FareRequestDto
                .builder()
                .lineFares(List.of(1, 2, 3))
                .build();

        int fare = fareLinePolicy.calculate(dto);
        assertThat(fare).isEqualTo(6);
    }
}