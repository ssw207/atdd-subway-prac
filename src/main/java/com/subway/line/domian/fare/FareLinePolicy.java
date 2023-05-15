package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

import java.util.Objects;

public class FareLinePolicy implements FarePolicy {

    @Override
    public int calculate(FareRequestDto dto) {
        return dto.lineFares()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(f -> f)
                .sum();
    }
}
