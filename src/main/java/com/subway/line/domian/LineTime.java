package com.subway.line.domian;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineTime {

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private int term;

    public LineTime(LocalTime startTime, LocalTime endTime, int term) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.term = term;
    }
}