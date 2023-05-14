package com.subway.line.domian.fare;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FareDistanceBasic extends AbstractFareDistance {

    private static final int FARE = 1250;
    private static final int MAX_DISTANCE = 10;

    private final int distance;

    @Override
    public FareDistance calculate() {
        if (distance <= 0) {
            return new FareDistanceEnd(FARE);
        }

        return new FareDistanceLevelOne(distance - MAX_DISTANCE, FARE);
    }
}
