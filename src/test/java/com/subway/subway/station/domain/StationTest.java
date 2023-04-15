package com.subway.subway.station.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StationTest {

    @Test
    void 프록시일치() {
        assertThat(new Station(1L)).isEqualTo(new ProxyStation(1L));
    }

    static class ProxyStation extends Station {
        public ProxyStation(Long id) {
            super(id);
        }
    }
}