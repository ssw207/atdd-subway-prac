package com.subway.subway.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotFindPathException extends SubwayIllegalArgumentException {
    public CanNotFindPathException(String message) {
        super(message);
    }
}
