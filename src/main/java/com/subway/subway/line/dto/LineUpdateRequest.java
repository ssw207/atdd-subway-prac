package com.subway.subway.line.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineUpdateRequest {

    private Long id;
    private String name;
    private String color;
}
