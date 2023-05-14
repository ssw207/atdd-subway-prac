package com.subway.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FareCalculateException extends RuntimeException {
    public FareCalculateException(String message) {
        super(message);
    }
}
