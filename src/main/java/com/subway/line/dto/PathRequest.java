package com.subway.line.dto;

import com.subway.line.domian.PathType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public record PathRequest(
        Long source,
        Long target,
        PathType type,
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        LocalTime startTime) {
}
