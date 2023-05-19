package com.subway.line.dto;

import lombok.Builder;

@Builder
public record FareAddPolicyRequestDto(int age, int totalFare) {
}
