package com.subway.line.domian.fare;

import com.subway.line.dto.FareRequestDto;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

public class FareAgePolicy implements FarePolicy {

    public static final int DEFAULT_DISCOUNT = 350;

    @Override
    public int calculate(FareRequestDto dto) {
        int age = dto.age();
        int totalFare = dto.totalFare();

        FareAge fareAge = FareAge.of(age);
        return fareAge.calcFare.apply(totalFare);
    }

    @RequiredArgsConstructor
    enum FareAge {
        TEEN(age -> age >= 13 && age <= 18, totalFare -> (int) ((totalFare - DEFAULT_DISCOUNT) * 0.8)),
        ADULT(age -> age >= 19, totalFare -> totalFare);

        public static FareAge of(int age) {
            return Arrays.stream(values())
                    .filter(e -> e.match.test(age))
                    .findAny()
                    .orElse(ADULT);
        }
        
        private final IntPredicate match; // TODO 람다식 네이밍은 어떻게 해야할까?
        private final IntFunction<Integer> calcFare;
    }
}
