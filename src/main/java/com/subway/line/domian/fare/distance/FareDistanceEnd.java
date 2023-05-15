package com.subway.line.domian.fare.distance;

import com.subway.common.exception.FareCalculateException;
import lombok.Getter;

@Getter
public class FareDistanceEnd extends AbstractFareDistance {

    public FareDistanceEnd(int fare) {
        super(0, fare);
    }

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
