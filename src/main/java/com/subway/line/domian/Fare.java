package com.subway.line.domian;

public class Fare {

    public int calculate(FareRequestDto fareRequestDto) {
        int distance = fareRequestDto.distance();
        distance = distance - 10;

        if (distance <= 0) {
            return 1250;
        }

        if (distance <= 40) {
            return 1250 + calculateOverFare(distance);
        }

        distance = distance - 40;
        return 1250 + calculateOverFare(40) + calculateOverFare(distance);
    }

    private int calculateOverFare(int distance) {
        return (int) ((Math.ceil((distance - 1) / 5) + 1) * 100);
    }
}
