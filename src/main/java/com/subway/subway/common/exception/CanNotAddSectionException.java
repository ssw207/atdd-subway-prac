package com.subway.subway.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotAddSectionException extends SubwayIllegalArgumentException {
    public CanNotAddSectionException(String message) {
        super(message);
    }
}
