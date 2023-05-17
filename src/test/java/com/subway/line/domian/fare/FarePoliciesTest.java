package com.subway.line.domian.fare;

import com.subway.line.domian.fare.age.FareAgePolicy;
import com.subway.line.domian.fare.distance.FareDistancePolicy;
import com.subway.line.domian.fare.line.FareLinePolicy;
import com.subway.line.dto.FareRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FarePoliciesTest {

    @Test
    void 추가_요금_게산() {
        FarePolicies farePolicies = new FarePolicies();
        farePolicies.addFareAddPolicy(new FareDistancePolicy());
        farePolicies.addFareAddPolicy(new FareLinePolicy());

        FareRequestDto fareRequestDto = FareRequestDto.builder()
                .distance(59)
                .lineFare(6)
                .build();

        int fare = farePolicies.calculate(fareRequestDto);
        assertThat(fare).isEqualTo(2250 + 6);
    }

    @Test
    void 추가_비율_요금_게산() {
        int 기본요금 = 1250;
        int 노선추가요금 = 100;
        int 총요금 = (int) ((기본요금 + 노선추가요금 - 350) * 0.8);

        FarePolicies farePolicies = new FarePolicies();
        farePolicies.addFareAddPolicy(new FareLinePolicy());
        farePolicies.addFareRatioPolicy(new FareAgePolicy());

        FareRequestDto fareRequestDto = FareRequestDto.builder()
                .lineFare(노선추가요금)
                .age(13)
                .build();

        int fare = farePolicies.calculate(fareRequestDto);
        assertThat(fare).isEqualTo(총요금);
    }

    @Test
    void 비율_요금_게산() {
        int 기본요금 = 1250;
        int 총요금 = (int) ((기본요금 - 350) * 0.8);

        FarePolicies farePolicies = new FarePolicies();
        farePolicies.addFareRatioPolicy(new FareAgePolicy());

        FareRequestDto fareRequestDto = FareRequestDto.builder()
                .age(13)
                .build();

        int fare = farePolicies.calculate(fareRequestDto);
        assertThat(fare).isEqualTo(총요금);
    }

}