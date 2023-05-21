package com.subway.station.service;

import com.subway.station.domain.Station;
import com.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public Long save(Station station) {
        return stationRepository.save(station).getId();
    }
}
