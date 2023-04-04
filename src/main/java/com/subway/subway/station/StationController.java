package com.subway.subway.station;

import com.subway.subway.station.dto.StationSaveRequest;
import com.subway.subway.station.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("stations")
@RestController
public class StationController {

    private final StationService stationService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody StationSaveRequest request) {
        Long id = stationService.save(request.toEntity());
        return ResponseEntity.created(URI.create("/stations/" + id)).body(id);
    }
}
