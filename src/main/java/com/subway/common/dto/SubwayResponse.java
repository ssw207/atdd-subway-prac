package com.subway.common.dto;

import com.subway.common.ErrorResponseCode;

public record SubwayResponse(String code, String message, Object body) {

    private static final String EMPTY = "Empty";

    public static SubwayResponse of(ErrorResponseCode code) {
        return new SubwayResponse(code.getCode(), code.getMessage(), EMPTY);
    }
}
