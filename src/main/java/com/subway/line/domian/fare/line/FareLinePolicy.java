package com.subway.line.domian.fare.line;

import com.subway.line.domian.fare.FarePolicy;
import com.subway.line.dto.FareRequestDto;

public class FareLinePolicy implements FarePolicy<FareRequestDto> {

    @Override
    public int calculate(FareRequestDto dto) {
        return dto.lineFare();
    }
}
