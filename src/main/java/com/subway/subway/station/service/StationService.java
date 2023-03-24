package com.subway.subway.station.service;

import com.subway.subway.station.domain.Station;
import com.subway.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public Long save(Station station) {
        return stationRepository.save(station).getId();
    }

    public Station findById(long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
