package com.subway.line.dto;

import lombok.Builder;

@Builder
public record FareRatioPolicyRequestDto(int distance, int lineFare) {
}
