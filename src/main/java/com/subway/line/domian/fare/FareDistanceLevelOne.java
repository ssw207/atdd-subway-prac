package com.subway.line.domian.fare;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FareDistanceLevelOne implements FareDistance {

    private static final int MAX_DISTANCE = 40;

    private final int distance;
    private final int fare;

    @Override
    public FareDistance calculate() {
        if (distance <= MAX_DISTANCE) {
            return new FareDistanceEnd(fare + calculateOverFare(distance));
        }

        return new FareDistanceLevelTwo(distance - MAX_DISTANCE, fare + calculateOverFare(MAX_DISTANCE));
    }

    private int calculateOverFare(int distance) {
        return (int) ((Math.ceil((distance - 1) / 5) + 1) * 100);
    }
}
