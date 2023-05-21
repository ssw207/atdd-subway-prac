package com.subway.line.domian.fare.line;

import com.subway.line.domian.fare.FareFlatPolicy;
import com.subway.line.dto.FareRequestDto;

public class FareLinePolicy implements FareFlatPolicy {

    @Override
    public int calculate(FareRequestDto dto) {
        return dto.lineFare();
    }
}
