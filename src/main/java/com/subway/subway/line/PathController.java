package com.subway.subway.line;

import com.subway.subway.line.domian.Path;
import com.subway.subway.line.dto.PathResponse;
import com.subway.subway.line.servcie.PathService;
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
    public PathResponse findPath(Long source, Long target) {
        Path path = pathService.findPath(source, target);
        return PathResponse.of(path);
    }
}
