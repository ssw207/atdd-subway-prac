package com.subway.common.doimain;

import com.subway.common.exception.path.CanNotFindPathExceptionByNotConnected;
import com.subway.common.exception.path.CanNotFindPathExceptionByNotExistsStation;
import com.subway.common.exception.path.CanNotFindPathExceptionBySamePath;
import com.subway.common.exception.section.CanNotAddMiddleSectionExceptionByDistanceLonger;
import com.subway.common.exception.section.CanNotAddSectionExceptionByAlreadySaved;
import com.subway.common.exception.section.CanNotAddSectionExceptionByNotConnected;
import com.subway.common.exception.section.CanNotRemoveSectionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorResponseCode {

    CAN_NOT_ADD_SECTION_BY_SAME_SECTION(BAD_REQUEST, "0001", "구간을 추가 할 수 없습니다. 이미 저장된 구간입니다.", List.of(CanNotAddSectionExceptionByAlreadySaved.class)),
    CAN_NOT_ADD_SECTION_BY_NOT_CONNECTED(BAD_REQUEST, "0002", "구간을 추가 할 수 없습니다. 열결되지 않은 구간입니다.", List.of(CanNotAddSectionExceptionByNotConnected.class)),
    CAN_NOT_ADD_MIDDLE_SECTION_BY_DISTANCE_LONGER(BAD_REQUEST, "0003", "중간 구간을 추가 할 수 없습니다. 기존 구간 거리보다 더 길수 없습니다.", List.of(CanNotAddMiddleSectionExceptionByDistanceLonger.class)),
    SECTION_REMOVE_FAIL(BAD_REQUEST, "1001", "구간을 삭제 할 수 업습니다.", List.of(CanNotRemoveSectionException.class)),
    CAN_NOT_FIND_PATH_BY_SAME_STATION(BAD_REQUEST, "2001", "경로를 조회할 수 없습니다. 출발역과 도착역이 같습니다.", List.of(CanNotFindPathExceptionBySamePath.class)),
    CAN_NOT_FIND_PATH_BY_NOT_CONNECTED(BAD_REQUEST, "2002", "경로를 조회할 수 없습니다. 연결되어 있지 않은 역입니다.", List.of(CanNotFindPathExceptionByNotConnected.class)),
    CAN_NOT_FIND_PATH_BY_NOT_EXISTS_STATION(BAD_REQUEST, "2003", "경로를 조회할 수 없습니다. 존재하지 않는 역 입니다.", List.of(CanNotFindPathExceptionByNotExistsStation.class)),
    COMMON_EXCEPTION(INTERNAL_SERVER_ERROR, "0000", "예외가 발생 했습니다.", List.of(Exception.class));

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
