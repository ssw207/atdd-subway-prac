package com.subway.subway.common;

import com.subway.subway.common.exception.CanNotRemoveSectionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ErrorResponseCode {

    SECTION_REMOVE_FAIL("1001", "구간을 삭제 할 수 업습니다.", List.of(CanNotRemoveSectionException.class)),
    COMMON_EXCEPTION("0000", "예외가 발생 했습니다.", List.of(Exception.class));

    private final String code;
    private final String message;
    private final List<Class<?>> exceptionClasses;

    public static ErrorResponseCode from(Exception exception) {
        return Arrays.stream(values())
                .filter(errorResponseCode -> errorResponseCode.getExceptionClasses().contains(exception.getClass()))
                .findFirst()
                .orElse(COMMON_EXCEPTION);
    }
}
