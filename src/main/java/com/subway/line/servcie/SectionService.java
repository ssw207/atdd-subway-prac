package com.subway.line.servcie;

import com.subway.line.domian.Line;
import com.subway.line.dto.SectionSaveRequest;
import com.subway.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class SectionService {

    private final StationRepository stationRepository;
    private final LineService lineService;

    @Transactional
    public void saveSection(long lineId, SectionSaveRequest request) {
        Line line = lineService.findById(lineId);
        line.add(request.toEntity(id -> stationRepository.findById(id).orElseThrow()));
    }

    @Transactional
    public void delete(long lineId, long stationId) {
        Line line = lineService.findById(lineId);
        line.removeSection(stationId);
    }
}
