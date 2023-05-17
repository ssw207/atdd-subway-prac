package com.subway.line.domian.fare.distance;

public class FareDistanceBasic extends AbstractFareDistance {

    private static final int MAX_DISTANCE = 10;

    public FareDistanceBasic(int distance) {
        super(distance, 0);
    }

    @Override
    public FareDistance calculate() {
        if (distance <= 0) {
            return new FareDistanceEnd(0);
        }

        return new FareDistanceLevelOne(distance - MAX_DISTANCE, 0);
    }
}
