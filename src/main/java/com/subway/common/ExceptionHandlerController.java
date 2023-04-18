package com.subway.common;

import com.subway.common.dto.SubwayResponse;
import com.subway.common.exception.SubwayIllegalArgumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<SubwayResponse> handleCanNotAddSectionException(SubwayIllegalArgumentException ex) {
        ErrorResponseCode code = ErrorResponseCode.from(ex);
        SubwayResponse response = SubwayResponse.of(code);
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
}
