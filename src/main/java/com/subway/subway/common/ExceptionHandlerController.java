package com.subway.subway.common;

import com.subway.subway.common.dto.SubwayResponse;
import com.subway.subway.common.exception.SubwayIllegalArgumentException;
import com.subway.subway.common.exception.section.CanNotAddSectionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseCode> handleStationException(SubwayIllegalArgumentException ex) {
        ErrorResponseCode code = ErrorResponseCode.from(ex);
        return new ResponseEntity<>(code, code.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<SubwayResponse> handleCanNotAddSectionException(CanNotAddSectionException ex) {
        ErrorResponseCode code = ErrorResponseCode.from(ex);
        SubwayResponse response = SubwayResponse.of(code);
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
}
