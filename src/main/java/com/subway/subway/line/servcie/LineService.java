package com.subway.subway.line.servcie;

import com.subway.subway.line.domian.Line;
import com.subway.subway.line.dto.LineSaveRequest;
import com.subway.subway.line.repository.LineRepository;
import com.subway.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public Line save(LineSaveRequest lineRequest) {
        Line line = lineRequest.toEntity(id -> stationRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        return lineRepository.save(line);
    }

    public Line findById(long id) {
        return lineRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}

