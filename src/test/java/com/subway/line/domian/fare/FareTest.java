package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FareTest {

    @Test
    void 추가요금_계산() {
        FareRequestDto fareRequestDto = FareRequestDto.builder()
                .distance(59)
                .lineFare(6)
                .build();

        Fare fare = new Fare();
        assertThat(fare.calculate(fareRequestDto)).isEqualTo(2250 + 6);
    }

    @Test
    void 비율요금_계산() {
        int 기본요금 = 1250;
        int 노선추가요금 = 100;
        double 할인률 = 0.8;
        int 청소년요금 = (int) ((기본요금 + 노선추가요금 - 350) * 할인률);

        FareRequestDto fareRequestDto = FareRequestDto.builder()
                .age(15)
                .lineFare(100)
                .build();

        Fare fare = new Fare();
        assertThat(fare.calculate(fareRequestDto)).isEqualTo(청소년요금);
    }
}