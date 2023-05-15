package com.subway.line.dto;

import lombok.Builder;

@Builder
public record FareRequestDto(int distance, int lineFare) {
}
