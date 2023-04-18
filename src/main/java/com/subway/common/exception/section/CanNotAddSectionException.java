package com.subway.common.exception.section;

import com.subway.common.exception.SubwayIllegalArgumentException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotAddSectionException extends SubwayIllegalArgumentException {
    public CanNotAddSectionException(String message) {
        super(message);
    }
}
