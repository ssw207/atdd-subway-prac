package com.subway.line.domian;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FareDistanceLevelTwo implements FareDistance {

    private final int distance;
    private final int fare;

    @Override
    public FareDistance calculate() {
        return new FareDistanceEnd(fare + calculateOverFare(distance));
    }

    private int calculateOverFare(int distance) {
        return (int) ((Math.ceil((distance - 1) / 8) + 1) * 100);
    }
}
