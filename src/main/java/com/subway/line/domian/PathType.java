package com.subway.line.domian;

import com.subway.common.exception.path.CanNotFindPathException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum PathType {
    DISTANCE("DISTANCE", Section::getDistance),
    DURATION("DURATION", Section::getDuration);

    private final String code; // enum name은 변경될수 있으므로 코드를 지정 한다
    private final Function<Section, Integer> weightFunction; // 경로 탐색시 어떤 필드 기준으로 탐색할지 결정

    public static PathType of(String type) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(type))
                .findAny()
                .orElseThrow(() -> new CanNotFindPathException("유효하지 않은 경로 타입입니다. " + type));
    }

    public int getWeight(Section section) {
        return weightFunction.apply(section);
    }
}
