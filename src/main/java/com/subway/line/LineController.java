package com.subway.line;

import com.subway.line.domian.Line;
import com.subway.line.dto.LineResponse;
import com.subway.line.dto.LineSaveRequest;
import com.subway.line.dto.LineUpdateRequest;
import com.subway.line.dto.SectionSaveRequest;
import com.subway.line.servcie.LineService;
import com.subway.line.servcie.SectionService;
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
    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<LineResponse> saveLine(@RequestBody LineSaveRequest request) {
        Line line = lineService.save(request);
        return ResponseEntity.created(URI.create("/lines/" + line.getId())).body(LineResponse.of(line));
    }

    @GetMapping("/{id}")
    public LineResponse findLineById(@PathVariable long id) {
        Line line = lineService.findById(id);
        return LineResponse.of(line);
    }

    @GetMapping
    public List<LineResponse> findAllLines() {
        List<Line> lines = lineService.findAll();
        return LineResponse.of(lines);
    }

    @PutMapping("/{id}")
    public void updateLine(@RequestBody LineUpdateRequest request) {
        lineService.update(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable long id) {
        lineService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{lineId}/sections")
    public ResponseEntity<Void> saveSection(@PathVariable long lineId, @RequestBody SectionSaveRequest request) {
        sectionService.saveSection(lineId, request);
        return ResponseEntity.created(URI.create(String.format("/lines/%d", lineId))).build();
    }

    @DeleteMapping("/{lineId}/sections")
    public void deleteSection(@PathVariable long lineId, long stationId) {
        sectionService.delete(lineId, stationId);
    }
}
