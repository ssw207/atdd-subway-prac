package com.subway.line.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FareRequestDto(int distance, List<Integer> lineFares) {
    public static FareRequestDto ofDistancePolicy(int distance) {
        return new FareRequestDto(distance, null);
    }
}
