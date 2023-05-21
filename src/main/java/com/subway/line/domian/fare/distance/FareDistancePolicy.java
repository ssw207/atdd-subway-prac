package com.subway.line.domian.fare.distance;

import com.subway.line.domian.fare.FareFlatPolicy;
import com.subway.line.dto.FareRequestDto;

public class FareDistancePolicy implements FareFlatPolicy {

    @Override
    public int calculate(FareRequestDto dto) {
        FareDistance fareDistance = new FareDistanceBasic(dto.distance());

        while (!fareDistance.isEnd()) {
            fareDistance = fareDistance.calculate();
        }

        return fareDistance.getFare();
    }
}
