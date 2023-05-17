package com.subway.line.domian.fare;

import com.subway.line.domian.fare.distance.FareDistancePolicy;
import com.subway.line.domian.fare.line.FareLinePolicy;
import com.subway.line.dto.FareRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FarePoliciesTest {

    @Test
    void 추가_요금_게산() {
        FarePolicies farePolicies = new FarePolicies();
        farePolicies.addFareTypeAddPolicy(new FareDistancePolicy());
        farePolicies.addFareTypeAddPolicy(new FareLinePolicy());

        FareRequestDto fareRequestDto = FareRequestDto.builder()
                .distance(59)
                .lineFare(6)
                .build();

        int fare = farePolicies.calc(fareRequestDto);
        assertThat(fare).isEqualTo(2250 + 6);
    }
}