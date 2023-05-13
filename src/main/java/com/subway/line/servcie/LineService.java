package com.subway.line.servcie;

import com.subway.line.domian.Line;
import com.subway.line.dto.LineSaveRequest;
import com.subway.line.dto.LineUpdateRequest;
import com.subway.line.repository.LineRepository;
import com.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional
    public Line save(LineSaveRequest lineRequest) {
        Line line = lineRequest.toEntity(id -> stationRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        return lineRepository.save(line);
    }

    public Line findById(long id) {
        return lineRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Line> findAll() {
        return lineRepository.findAll();
    }

    @Transactional
    public void update(LineUpdateRequest request) {
        Line line = this.findById(request.id());
        line.update(request.name(), request.color());
    }

    @Transactional
    public void deleteById(long id) {
        lineRepository.deleteById(id);
    }
}

