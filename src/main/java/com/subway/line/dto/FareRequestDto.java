package com.subway.line.dto;

import com.subway.line.domian.Path;
import lombok.Builder;

@Builder
public record FareRequestDto(int distance, int lineFare, int age, int totalFare) {

    public static FareRequestDto of(Path path, int age) {
        return FareRequestDto.builder()
                .distance(path.getDistance())
                .lineFare(path.getTotalLineFare())
                .age(age)
                .build();
    }

    public FareRequestDto toRatioFareRequestDto(int totalFare) {
        return builder()
                .age(age)
                .totalFare(totalFare)
                .build();
    }
}
