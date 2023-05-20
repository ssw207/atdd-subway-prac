package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Fare {

    private final FarePolicies farePolicies;

    public int calculate(FareRequestDto dto) {
        return farePolicies.calculate(dto);
    }
}
