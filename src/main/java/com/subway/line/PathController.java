package com.subway.line;

import com.subway.line.domian.Path;
import com.subway.line.domian.fare.Fare;
import com.subway.line.dto.FareRequestDto;
import com.subway.line.dto.PathRequest;
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
    private final Fare fare;

    @GetMapping
    public PathResponse findPath(PathRequest pathRequest, @AuthMemberPrincipal(nullable = true) AuthMember authMember) {  // Date로 변환로직 필요
        Path path = pathService.findPath(pathRequest);
        int totalFare = fare.calculate(FareRequestDto.of(path, authMember.age()));
        return PathResponse.of(path, totalFare);
    }
}
