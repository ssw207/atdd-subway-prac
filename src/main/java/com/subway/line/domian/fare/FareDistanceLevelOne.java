package com.subway.line.domian.fare;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FareDistanceLevelOne extends AbstractFareDistance {

    private static final int MAX_DISTANCE = 40;
    private static final int PER_DISTANCE = 5;

    private final int distance;
    private final int fare;

    @Override
    public FareDistance calculate() {
        if (distance <= MAX_DISTANCE) {
            return new FareDistanceEnd(calculateFare(distance));
        }

        return new FareDistanceLevelTwo(distance - MAX_DISTANCE, calculateFare(MAX_DISTANCE));
    }

    private int calculateFare(int distance) {
        return fare + calculateOverFare(distance, PER_DISTANCE);
    }
}
