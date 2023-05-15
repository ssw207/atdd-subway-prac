package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

public class FareLinePolicy implements FarePolicy {

    @Override
    public int calculate(FareRequestDto dto) {
        if (CollectionUtils.isEmpty(dto.lineFares())) {
            return 0;
        }

        return dto.lineFares()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(f -> f)
                .sum();
    }
}
