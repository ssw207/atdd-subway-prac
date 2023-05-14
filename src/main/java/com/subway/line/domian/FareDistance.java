package com.subway.line.domian;

import com.subway.common.exception.FareCalculateException;

public interface FareDistance {

    FareDistance calculate();

    default int getFare() {
        throw new FareCalculateException("계산 완료후 요금을 조회할 수 있습니다.");
    }

    default boolean isEnd() {
        return false;
    }
}
