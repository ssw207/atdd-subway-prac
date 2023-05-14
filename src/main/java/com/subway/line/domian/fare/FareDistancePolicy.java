package com.subway.line.domian.fare;

public class FareDistancePolicy implements FarePolicy {

    @Override
    public int calculate(int distance) {
        FareDistance fareDistance = new FareDistanceBasic(distance);

        while (!fareDistance.isEnd()) {
            fareDistance = fareDistance.calculate();
        }

        return fareDistance.getFare();
    }
}
