package com.subway.line.domian;

public class FareDistanceBasic implements FareDistance {

    @Override
    public FareDistance calculate() {
        return new FareDistanceEnd(1250);
    }
}
