package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotFindPathExceptionByNotConnected;
import com.subway.subway.common.exception.CanNotFindPathExceptionByNotExistsStation;
import com.subway.subway.common.exception.CanNotFindPathExceptionBySamePath;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
        allSections.forEach(sections -> {
            for (int i = 0; i < sections.size(); i++) {
                graph.addEdgeAndWeight(sections.get(i));
            }
        });
    }

    private static void addVertex(SubwayGraph graph, List<Line> lines) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    public Path findPath(long source, long target) {
        if (source == target) {
            throw new CanNotFindPathExceptionBySamePath();
        }

        if (graph.notExistsStationId(source) || graph.notExistsStationId(target)) {
            throw new CanNotFindPathExceptionByNotExistsStation();
        }

        return graph.getPath(source, target)
                .orElseThrow(CanNotFindPathExceptionByNotConnected::new);
    }
}
