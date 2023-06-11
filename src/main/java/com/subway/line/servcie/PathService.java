package com.subway.line.servcie;

import com.subway.line.domian.Path;
import com.subway.line.domian.SubwayMap;
import com.subway.line.dto.PathRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathService {

    private final LineService lineService;

    public Path findPath(PathRequest pathRequest) {
        return SubwayMap.of(lineService.findAll())
                .findPath(pathRequest);
    }
}
