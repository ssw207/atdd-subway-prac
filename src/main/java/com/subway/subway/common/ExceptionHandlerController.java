package com.subway.subway.common;

import com.subway.subway.common.exception.CanNotRemoveSectionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(CanNotRemoveSectionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseCode handleCanNotRemoveSectionException(CanNotRemoveSectionException ex) {
        return ErrorResponseCode.from(ex);
    }
}
