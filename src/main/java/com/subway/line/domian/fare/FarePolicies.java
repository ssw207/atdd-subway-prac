package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;

import java.util.ArrayList;
import java.util.List;


public class FarePolicies {

    private static final int DEFAULT_FARE = 1250;

    private final List<FareFlatPolicy> addFarePolicyList = new ArrayList<>();
    private final List<FareTotalRatePolicy> ratioFarePolicyList = new ArrayList<>();

    public void addFareAddPolicy(FareFlatPolicy farePolicy) {
        addFarePolicyList.add(farePolicy);
    }

    public void addFareRatioPolicy(FareTotalRatePolicy farePolicy) {
        ratioFarePolicyList.add(farePolicy);
    }

    public int calculate(FareRequestDto dto) {
        int totalFare = DEFAULT_FARE;

        if (!addFarePolicyList.isEmpty()) {
            totalFare += calculateFlatFare(dto);
        }

        if (ratioFarePolicyList.isEmpty()) {
            return totalFare;
        }

        return calculateRateFare(dto.toRatioFareRequestDto(totalFare));
    }

    private int calculateFlatFare(FareRequestDto dto) {
        return calculateFare(dto, addFarePolicyList);
    }

    private int calculateRateFare(FareTotalRateRequestDto ratioFareRequestDto) {
        return calculateFare(ratioFareRequestDto, ratioFarePolicyList);
    }

    private <DTO, POLICY extends FarePolicy<DTO>> int calculateFare(DTO dto, List<POLICY> policyList) {
        return policyList.stream()
                .mapToInt(p -> p.calculate(dto))
                .sum();
    }
}
