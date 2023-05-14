package com.subway.line.domian.fare;

public class FareDistanceLevelTwo extends AbstractFareDistance {

    private static final int PER_DISTANCE = 8;

    public FareDistanceLevelTwo(int distance, int fare) {
        super(distance, fare);
    }

    @Override
    public FareDistance calculate() {
        return new FareDistanceEnd(fare + calculateOverFare(distance, PER_DISTANCE));
    }
}
