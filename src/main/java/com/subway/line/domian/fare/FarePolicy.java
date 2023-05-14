package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

public interface FarePolicy {

    int calculate(FareRequestDto distance);
}
