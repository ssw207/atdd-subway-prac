package com.subway.line.domian.fare.line;

import com.subway.line.domian.fare.FarePolicy;
import com.subway.line.dto.FareRequestDto;

public class FareLinePolicy implements FarePolicy {

    @Override
    public int calculate(FareRequestDto dto) {
        return dto.lineFare();
    }
}
