package com.subway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ConfigurationPropertiesScan("com.subway")
@EnableJpaAuditing
@SpringBootApplication
public class AtddSubwayPracApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtddSubwayPracApplication.class, args);
    }

}
