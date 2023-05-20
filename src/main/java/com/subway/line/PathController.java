package com.subway.line;

import com.subway.line.domian.Path;
import com.subway.line.domian.PathType;
import com.subway.line.dto.PathResponse;
import com.subway.line.servcie.PathService;
import com.subway.member.dto.AuthMember;
import com.subway.member.resolver.AuthMemberPrincipal;
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
    public PathResponse findPath(Long source, Long target, String type, @AuthMemberPrincipal(nullable = true) AuthMember authMember) {
        Path path = pathService.findPath(source, target, PathType.of(type), authMember.age());
        return PathResponse.of(path);
    }
}
