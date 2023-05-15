package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

public class FareAgePolicy implements FarePolicy {

    @Override
    public int calculate(FareRequestDto distance) {
        throw new UnsupportedOperationException(); // TODO 구현 필요
    }
}
