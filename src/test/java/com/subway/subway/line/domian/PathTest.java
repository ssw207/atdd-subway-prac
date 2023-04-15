package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotFindPathException;
import com.subway.subway.line.LineFixture;
import com.subway.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PathTest {

    public static final long STATION_1 = 1L;
    public static final long STATION_2 = 2L;
    public static final long STATION_3 = 3L;
    public static final long STATION_4 = 4L;
    public SubwayMap subwayMap;

    @BeforeEach
    void setUp() {
        Line line1 = LineFixture.createLineHas2Section(STATION_1, STATION_2, STATION_3, 2);
        Line line2 = LineFixture.createLineHas2Section(STATION_1, STATION_4, STATION_3, 3);
        List<Line> lines = List.of(line1, line2);
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
        assertThatThrownBy(() -> subwayMap.findPath(STATION_1, STATION_1)).isInstanceOf(CanNotFindPathException.class);
    }

    private List<Long> convertToStationIds(Path path) {
        return path.stations().stream().map(Station::getId).toList();
    }
}