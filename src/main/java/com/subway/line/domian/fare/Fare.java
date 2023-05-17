package com.subway.line.domian.fare;

import com.subway.line.domian.fare.age.FareAgePolicy;
import com.subway.line.domian.fare.distance.FareDistancePolicy;
import com.subway.line.domian.fare.line.FareLinePolicy;
import com.subway.line.dto.FareRequestDto;

public class Fare {

    private final FarePolicies farePolicies;

    public Fare() {
        farePolicies = new FarePolicies();
        farePolicies.addFareAddPolicy(new FareDistancePolicy());
        farePolicies.addFareAddPolicy(new FareLinePolicy());
        farePolicies.addFareRatioPolicy(new FareAgePolicy());
    }

    public int calculate(FareRequestDto dto) {
        return farePolicies.calculate(dto);
    }
}
