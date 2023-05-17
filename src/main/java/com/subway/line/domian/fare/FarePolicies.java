package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

import java.util.ArrayList;
import java.util.List;

public class FarePolicies {

    private final List<FarePolicy> addFarePolicyList = new ArrayList<>();

    public void addFareTypeAddPolicy(FarePolicy farePolicy) {
        addFarePolicyList.add(farePolicy);
    }

    public int calculate(FareRequestDto dto) {
        return addFarePolicyList.stream()
                .mapToInt(p -> p.calculate(dto))
                .sum();
    }
}
