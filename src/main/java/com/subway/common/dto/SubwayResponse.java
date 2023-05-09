package com.subway.common.dto;

import com.subway.common.doimain.ErrorResponseCode;

public record SubwayResponse(String code, String message, Object body) {

    private static final String EMPTY = "Empty";

    public static SubwayResponse of(ErrorResponseCode code) {
        return of(code, "");
    }

    public static SubwayResponse of(ErrorResponseCode code, String exceptionMessage) {
        String responseErrorMessage = String.format("%s %s", code.getMessage(), exceptionMessage);
        return new SubwayResponse(code.getCode(), responseErrorMessage, EMPTY);
    }
}
