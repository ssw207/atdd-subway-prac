package com.subway.line.domian.fare;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FareDistanceLevelTwo extends AbstractFareDistance {

    private static final int PER_DISTANCE = 8;

    private final int distance;
    private final int fare;

    @Override
    public FareDistance calculate() {
        return new FareDistanceEnd(fare + calculateOverFare(distance, PER_DISTANCE));
    }
}
