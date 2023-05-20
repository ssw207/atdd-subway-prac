package com.subway.line.domian.fare;

public interface FarePolicy<T> {

    int calculate(T dto);
}
