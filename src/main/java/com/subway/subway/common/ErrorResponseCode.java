package com.subway.subway.common;

import com.subway.subway.common.exception.path.CanNotFindPathExceptionByNotConnected;
import com.subway.subway.common.exception.path.CanNotFindPathExceptionByNotExistsStation;
import com.subway.subway.common.exception.path.CanNotFindPathExceptionBySamePath;
import com.subway.subway.common.exception.section.CanNotRemoveSectionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ErrorResponseCode {

    SECTION_REMOVE_FAIL(HttpStatus.BAD_REQUEST, "1001", "구간을 삭제 할 수 업습니다.", List.of(CanNotRemoveSectionException.class)),
    CAN_NOT_FIND_PATH_BY_SAME_STATION(HttpStatus.BAD_REQUEST, "2001", "경로를 조회할 수 없습니다. 출발역과 도착역이 같습니다.", List.of(CanNotFindPathExceptionBySamePath.class)),
    CAN_NOT_FIND_PATH_BY_NOT_CONNECTED(HttpStatus.BAD_REQUEST, "2002", "경로를 조회할 수 없습니다. 연결되어 있지 않은 역입니다.", List.of(CanNotFindPathExceptionByNotConnected.class)),
    CAN_NOT_FIND_PATH_BY_NOT_EXISTS_STATION(HttpStatus.BAD_REQUEST, "2003", "경로를 조회할 수 없습니다. 존재하지 않는 역 입니다.", List.of(CanNotFindPathExceptionByNotExistsStation.class)),
    COMMON_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "0000", "예외가 발생 했습니다.", List.of(Exception.class));

    private final HttpStatus httpStatus;
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
