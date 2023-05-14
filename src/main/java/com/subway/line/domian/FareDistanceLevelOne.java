package com.subway.line.domian;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FareDistanceLevelOne implements FareDistance {

    private final int distance;
    private final int fare;

    @Override
    public FareDistance calculate() {
        return new FareDistanceEnd(fare + calculateOverFare(distance));
    }

    private int calculateOverFare(int distance) {
        return (int) ((Math.ceil((distance - 1) / 5) + 1) * 100);
    }
}
