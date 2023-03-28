package com.subway.subway.line;

import com.subway.subway.line.domian.Line;
import com.subway.subway.line.dto.LineResponse;
import com.subway.subway.line.dto.LineSaveRequest;
import com.subway.subway.line.dto.LineUpdateRequest;
import com.subway.subway.line.servcie.LineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("lines")
@RestController
@RequiredArgsConstructor
public class LineController {

    private final LineService lineService;

    @PostMapping
    public ResponseEntity<LineResponse> save(@RequestBody LineSaveRequest request) {
        Line line = lineService.save(request);
        return ResponseEntity.created(URI.create("/lines/" + line.getId())).body(LineResponse.of(line));
    }

    @GetMapping("/{id}")
    public LineResponse findById(@PathVariable long id) {
        Line line = lineService.findById(id);
        return LineResponse.of(line);
    }

    @GetMapping
    public List<LineResponse> findAll() {
        List<Line> lines = lineService.findAll();
        return LineResponse.of(lines);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody LineUpdateRequest request) {
        lineService.update(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        lineService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
