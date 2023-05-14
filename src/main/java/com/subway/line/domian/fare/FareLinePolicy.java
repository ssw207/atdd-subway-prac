package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

public class FareLinePolicy implements FarePolicy {

    @Override
    public int calculate(FareRequestDto dto) {
        return dto.lineFares().stream().mapToInt(f -> f).sum();
    }
}
