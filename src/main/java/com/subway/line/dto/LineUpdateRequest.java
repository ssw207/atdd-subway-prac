package com.subway.line.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LineUpdateRequest(@NotNull Long id, String name, String color) {

}
