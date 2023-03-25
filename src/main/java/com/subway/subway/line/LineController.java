package com.subway.subway.line;

import com.subway.subway.line.domian.Line;
import com.subway.subway.line.dto.LineResponse;
import com.subway.subway.line.dto.LineSaveRequest;
import com.subway.subway.line.servcie.LineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("lines")
@RestController
@RequiredArgsConstructor
public class LineController {

    private final LineService lineService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody LineSaveRequest request) {
        Line line = lineService.save(request);
        return ResponseEntity.created(URI.create("/lines/" + line.getId())).build();
    }

    @GetMapping("/{id}")
    public LineResponse findById(@PathVariable long id) {
        Line line = lineService.findById(id);
        return LineResponse.of(line);
    }
}
