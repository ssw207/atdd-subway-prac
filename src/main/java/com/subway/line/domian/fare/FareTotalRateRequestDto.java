package com.subway.line.domian.fare;

import lombok.Builder;

@Builder
public record FareTotalRateRequestDto(int totalFare, int age) {
}
