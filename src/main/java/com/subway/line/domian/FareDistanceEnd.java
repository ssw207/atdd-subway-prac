package com.subway.line.domian;

import com.subway.common.exception.FareCalculateException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FareDistanceEnd implements FareDistance {

    private final int fare;

    @Override
    public FareDistance calculate() {
        throw new FareCalculateException("계산이 종료 되었습니다.");
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public int getFare() {
        return fare;
    }
}
