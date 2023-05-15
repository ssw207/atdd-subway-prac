package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

import java.util.List;

public class Fare {

    private final List<FarePolicy> policies = List.of(new FareDistancePolicy(), new FareLinePolicy());

    public int calculate(FareRequestDto dto) {
        return policies.stream()
                .mapToInt(p -> p.calculate(dto))
                .sum();
    }
}
