package com.subway.subway.common.exception.path;

import com.subway.subway.common.exception.SubwayIllegalArgumentException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotFindPathException extends SubwayIllegalArgumentException {
    public CanNotFindPathException(String message) {
        super(message);
    }
}
