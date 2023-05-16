package com.subway.line.dto;

import lombok.Builder;

@Builder
public record FareRequestDto(int distance, int lineFare, int age,
                             int totalFare) { // TODO totalFare는 비율요금에만 필요하다 dto 분리를 고려한다
}
