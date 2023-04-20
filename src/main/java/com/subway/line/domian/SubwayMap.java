package com.subway.line.domian;

import com.subway.common.exception.path.CanNotFindPathExceptionByNotConnected;
import com.subway.common.exception.path.CanNotFindPathExceptionByNotExistsStation;
import com.subway.common.exception.path.CanNotFindPathExceptionBySamePath;
import com.subway.station.domain.Station;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SubwayMap {

    private final SubwayGraph graph;

    public static SubwayMap of(List<Line> lines) {
        return new SubwayMap(createSubwayGraph(lines));
    }

    private static SubwayGraph createSubwayGraph(List<Line> lines) {
        SubwayGraph graph = new SubwayGraph();

        // 정점 추가
        addVertex(graph, lines);

        // 간선 추가
        addEdge(graph, convertToSectionsList(lines));

        return graph;
    }

    private static List<Sections> convertToSectionsList(List<Line> lines) {
        return lines.stream()
                .map(Line::getSections)
                .toList();
    }

    private static void addEdge(SubwayGraph graph, List<Sections> allSections) {
        allSections.forEach(graph::addEdgeAndWeight);
    }

    private static void addVertex(SubwayGraph graph, List<Line> lines) {
        getUniqueStations(lines).forEach(graph::addVertex);
    }

    private static Stream<Station> getUniqueStations(List<Line> lines) {
        return lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct();
    }

    public Path findPath(long source, long target) {
        validate(source, target);

        return graph.getPath(source, target)
                .orElseThrow(CanNotFindPathExceptionByNotConnected::new);
    }

    private void validate(long source, long target) {
        if (source == target) {
            throw new CanNotFindPathExceptionBySamePath();
        }

        if (graph.notExistsStationId(source) || graph.notExistsStationId(target)) {
            throw new CanNotFindPathExceptionByNotExistsStation();
        }
    }
}
