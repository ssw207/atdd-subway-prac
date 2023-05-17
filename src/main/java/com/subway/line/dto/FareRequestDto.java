package com.subway.line.dto;

import lombok.Builder;

@Deprecated
@Builder
public record FareRequestDto(int distance, int lineFare, int age, int totalFare) {

    public FareRequestDto toRatioFareRequestDto(int totalFare) {
        return builder()
                .age(age)
                .totalFare(totalFare)
                .build();
    }
}
