package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

public class FareAgePolicy implements FarePolicy {

    @Override
    public int calculate(FareRequestDto dto) {
        int age = dto.age();
        int totalFare = dto.totalFare();

        if (age >= 13 && age <= 18) {
            return (int) ((totalFare - 350) * 0.8);
        }

        throw new UnsupportedOperationException(); // TODO 구현 필요
    }
}
