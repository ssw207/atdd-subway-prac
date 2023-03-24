package com.subway.subway.station.controller;

import com.subway.subway.station.service.StationSaveRequest;
import com.subway.subway.station.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("stations")
@RestController
public class StationController {

    private final StationService stationService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody StationSaveRequest request) {
        Long id = stationService.save(request.toEntity());
        return ResponseEntity.created(URI.create("/stations/" + id)).build();
    }
}
