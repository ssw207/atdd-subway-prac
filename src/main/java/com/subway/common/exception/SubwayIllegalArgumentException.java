package com.subway.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SubwayIllegalArgumentException extends IllegalArgumentException {

    public SubwayIllegalArgumentException(String message) {
        super(message);
    }
}
