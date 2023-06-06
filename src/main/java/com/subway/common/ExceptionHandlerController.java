package com.subway.common;

import com.subway.common.doimain.ErrorResponseCode;
import com.subway.common.dto.SubwayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<SubwayResponse> handleCanNotAddSectionException(RuntimeException ex) {
        ex.printStackTrace();
        ErrorResponseCode code = ErrorResponseCode.from(ex);
        SubwayResponse response = SubwayResponse.of(code, ex.getMessage());
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
}
