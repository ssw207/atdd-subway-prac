package com.subway.subway.line;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineSaveRequest {

    @NotEmpty
    private String name;
    private long upStationId;
    private long downStationId;
    private int fare;
}
