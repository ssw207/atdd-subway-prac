package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

import java.util.ArrayList;
import java.util.List;


public class FarePolicies {

    private static final int DEFAULT_FARE = 1250;

    private final List<FarePolicy> addFarePolicyList = new ArrayList<>();
    private final List<FarePolicy> ratioFarePolicyList = new ArrayList<>(); // TODO 추가/비율 요금정책의 인터페이스 분리를 고려한다

    public void addFareAddPolicy(FarePolicy farePolicy) {
        addFarePolicyList.add(farePolicy);
    }

    public void addFareRatioPolicy(FarePolicy farePolicy) {
        ratioFarePolicyList.add(farePolicy);
    }

    public int calculate(FareRequestDto dto) {
        int totalFare = DEFAULT_FARE;

        if (!addFarePolicyList.isEmpty()) {
            totalFare += calculateFare(dto, addFarePolicyList);
        }

        if (!ratioFarePolicyList.isEmpty()) {
            return calculateFare(dto.toRatioFareRequestDto(totalFare), ratioFarePolicyList);
        }

        return totalFare;
    }

    private int calculateFare(FareRequestDto dto, List<FarePolicy> policyList) {
        return policyList.stream()
                .mapToInt(p -> p.calculate(dto))
                .sum();
    }
}
