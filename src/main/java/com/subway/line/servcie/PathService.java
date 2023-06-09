package com.subway.line.servcie;

import com.subway.line.domian.Path;
import com.subway.line.domian.PathType;
import com.subway.line.domian.SubwayMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathService {

    private final LineService lineService;

    public Path findPath(long source, long target, PathType pathType) {
        return SubwayMap.of(lineService.findAll())
                .findPath(source, target, pathType);
    }
}
