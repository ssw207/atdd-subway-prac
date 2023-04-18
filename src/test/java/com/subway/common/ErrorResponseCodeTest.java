package com.subway.common;

import com.subway.common.exception.section.CanNotRemoveSectionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseCodeTest {

    @Test
    void CanNotRemoveSectionException_예외_검증() {
        ErrorResponseCode code = ErrorResponseCode.from(new CanNotRemoveSectionException());
        assertThat(code).isEqualTo(ErrorResponseCode.SECTION_REMOVE_FAIL);
        assertThat(code.getCode()).isEqualTo("1001");
        assertThat(code.getMessage()).isEqualTo("구간을 삭제 할 수 업습니다.");
    }
}