package com.subway.line.dto;

import com.subway.line.domian.PathType;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Builder
public record PathRequest(
        Long source,
        Long target,
        PathType type,
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        LocalTime startTime) {
    public boolean isSourceAndTargetSame() {
        return source.equals(target);
    }

    public static PathRequest ofDistanceType(Long source, Long target) {
        return PathRequest.builder()
                .type(PathType.DISTANCE)
                .source(source)
                .target(target)
                .build();
    }

    public static PathRequest ofDurationType(Long source, Long target) {
        return PathRequest.builder()
                .type(PathType.DURATION)
                .source(source)
                .target(target)
                .build();
    }
}
