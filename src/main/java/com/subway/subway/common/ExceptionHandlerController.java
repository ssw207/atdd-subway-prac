package com.subway.subway.common;

import com.subway.subway.common.exception.SubwayIllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(SubwayIllegalArgumentException.class)
    public ResponseEntity<ErrorResponseCode> handleCanNotRemoveSectionException(SubwayIllegalArgumentException ex) {
        ErrorResponseCode code = ErrorResponseCode.from(ex);
        HttpStatus httpStatus = code.getHttpStatus();
        return new ResponseEntity<>(code, httpStatus);
    }
}
