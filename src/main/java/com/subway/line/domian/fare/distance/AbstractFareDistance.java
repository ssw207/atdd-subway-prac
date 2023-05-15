package com.subway.line.domian.fare.distance;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractFareDistance implements FareDistance {

    protected final int distance;
    protected final int fare;

    protected int calculateOverFare(int distance, int perDistance) {
        return (int) ((Math.ceil((distance - 1) / perDistance) + 1) * 100);
    }
}
