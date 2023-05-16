package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class FareAgePolicyTest {

    @Test
    void 비회원_요금_계산() {
        //given
        int 기본요금 = 1250;
        int age = 0;

        FareRequestDto dto = FareRequestDto.builder()
                .totalFare(기본요금)
                .age(age)
                .build();

        //when
        FarePolicy policy = new FareAgePolicy();
        int calculate = policy.calculate(dto);

        //then
        assertThat(calculate).isEqualTo(기본요금);
    }

    @ParameterizedTest
    @ValueSource(ints = {13, 18})
    void 청소년_요금_계산(int age) {
        //given
        int 기본요금 = 1250;
        double 할인률 = 0.8;
        int 청소년요금 = (int) ((기본요금 - 350) * 할인률);

        FareRequestDto dto = FareRequestDto.builder()
                .totalFare(기본요금)
                .age(age)
                .build();

        //when
        FarePolicy policy = new FareAgePolicy();
        int calculate = policy.calculate(dto);

        //then
        assertThat(calculate).isEqualTo(청소년요금);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 12})
    void 어린이_요금_계산(int age) {
        //given
        int 기본요금 = 1250;
        double 할인률 = 0.5;
        int 어린이요금 = (int) ((기본요금 - 350) * 할인률);

        FareRequestDto dto = FareRequestDto.builder()
                .totalFare(기본요금)
                .age(age)
                .build();

        //when
        FarePolicy policy = new FareAgePolicy();
        int calculate = policy.calculate(dto);

        //then
        assertThat(calculate).isEqualTo(어린이요금);
    }
}