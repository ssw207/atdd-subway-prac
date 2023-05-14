package com.subway.line.domian.fare;

public abstract class AbstractFareDistance implements FareDistance {

    protected int calculateOverFare(int distance, int perDistance) {
        return (int) ((Math.ceil((distance - 1) / perDistance) + 1) * 100);
    }
}
