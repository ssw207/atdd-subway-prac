package com.subway.line;

import com.subway.line.domian.Path;
import com.subway.line.domian.PathType;
import com.subway.line.dto.PathResponse;
import com.subway.line.servcie.PathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/paths")
@RestController
@RequiredArgsConstructor
public class PathController {

    private final PathService pathService;

    @GetMapping
    public PathResponse findPath(Long source, Long target, String type) { // TODO resolver로 개선필요
        Path path = pathService.findPath(source, target, PathType.of(type));
        return PathResponse.of(path);
    }
}
