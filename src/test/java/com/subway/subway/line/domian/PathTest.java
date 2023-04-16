package com.subway.subway.line.domian;

import com.subway.subway.common.exception.path.CanNotFindPathExceptionByNotConnected;
import com.subway.subway.common.exception.path.CanNotFindPathExceptionByNotExistsStation;
import com.subway.subway.common.exception.path.CanNotFindPathExceptionBySamePath;
import com.subway.subway.line.LineFixture;
import com.subway.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PathTest {

    private static final long STATION_1 = 1L;
    private static final long STATION_2 = 2L;
    private static final long STATION_3 = 3L;
    private static final long STATION_4 = 4L;
    private static final long STATION_NOT_CONNECTED_1 = 100L;
    private static final long STATION_NOT_CONNECTED_2 = 101L;
    private static final long STATION_NOT_CONNECTED_3 = 102L;
    private static final long STATION_NOT_EXISTS = 200L;

    private SubwayMap subwayMap;

    @BeforeEach
    void setUp() {
        Line connectedLine1 = LineFixture.createLineHas2Section(STATION_1, STATION_2, STATION_3, 2);
        Line connectedLine2 = LineFixture.createLineHas2Section(STATION_1, STATION_4, STATION_3, 3);
        Line notConnectedLine1 = LineFixture.createLineHas2Section(STATION_NOT_CONNECTED_1, STATION_NOT_CONNECTED_2, STATION_NOT_CONNECTED_3, 3);
        List<Line> lines = List.of(connectedLine1, connectedLine2, notConnectedLine1);
        subwayMap = SubwayMap.of(lines);
    }


    /**
     * 역1 - 노선1(2) - 역2
     * |             |
     * 노선2(3)         노선1(2)
     * |             |
     * 역4 - 노선2(3) - 역3
     */
    @Test
    void 경로조회() {
        //when
        Path path = subwayMap.findPath(STATION_1, STATION_3);

        //then
        assertThat(path.distance()).isEqualTo(4);
        assertThat(convertToStationIds(path)).containsExactly(STATION_1, STATION_2, STATION_3);
    }

    @Test
    void 출발역과_도착역이_같으면_조회_불가() {
        assertThatThrownBy(() -> subwayMap.findPath(STATION_1, STATION_1))
                .isInstanceOf(CanNotFindPathExceptionBySamePath.class);
    }

    @Test
    void 출발역과_도착역이_이어져있지_않으면_조회_불가() {
        assertThatThrownBy(() -> subwayMap.findPath(STATION_1, STATION_NOT_CONNECTED_1))
                .isInstanceOf(CanNotFindPathExceptionByNotConnected.class);
    }

    @Test
    void 없는_도착역을_조회하면_조회_불가() {
        assertThatThrownBy(() -> subwayMap.findPath(STATION_1, STATION_NOT_EXISTS))
                .isInstanceOf(CanNotFindPathExceptionByNotExistsStation.class);
    }

    @Test
    void 없는_출발역을_조회하면_조회_불가() {
        assertThatThrownBy(() -> subwayMap.findPath(STATION_NOT_EXISTS, STATION_1))
                .isInstanceOf(CanNotFindPathExceptionByNotExistsStation.class);
    }

    private List<Long> convertToStationIds(Path path) {
        return path.stations().stream().map(Station::getId).toList();
    }
}