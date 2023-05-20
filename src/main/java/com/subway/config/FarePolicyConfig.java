package com.subway.config;

import com.subway.line.domian.fare.FarePolicies;
import com.subway.line.domian.fare.age.FareAgePolicy;
import com.subway.line.domian.fare.distance.FareDistancePolicy;
import com.subway.line.domian.fare.line.FareLinePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FarePolicyConfig {

    @Bean
    public FarePolicies createFarePolicies() {
        FarePolicies farePolicies = new FarePolicies();
        farePolicies.addFareAddPolicy(new FareDistancePolicy());
        farePolicies.addFareAddPolicy(new FareLinePolicy());
        farePolicies.addFareRatioPolicy(new FareAgePolicy());
        return farePolicies;
    }
}
