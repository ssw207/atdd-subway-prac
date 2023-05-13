package com.subway.line.domian;

import com.subway.common.exception.path.CanNotFindPathExceptionByNotConnected;
import com.subway.common.exception.path.CanNotFindPathExceptionByNotExistsStation;
import com.subway.common.exception.path.CanNotFindPathExceptionBySamePath;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubwayMap {

    private final List<Line> lines;

    public static SubwayMap of(List<Line> lines) {
        return new SubwayMap(lines);
    }

    public Path findPath(long source, long target, PathType pathType) {
        if (source == target) {
            throw new CanNotFindPathExceptionBySamePath();
        }

        SubwayGraph graph = createSubwayGraph(pathType);

        if (graph.notExistsStationId(source) || graph.notExistsStationId(target)) {
            throw new CanNotFindPathExceptionByNotExistsStation();
        }

        return graph.getPath(source, target)
                .orElseThrow(CanNotFindPathExceptionByNotConnected::new);
    }

    private SubwayGraph createSubwayGraph(PathType pathType) {
        SubwayGraph graph = new SubwayGraph();

        // 정점 추가
        addVertex(graph, lines);

        List<Sections> allSections = convertToSectionsList(lines);

        // 간선 추가
        addEdgeAndWeight(graph, allSections, pathType);

        return graph;
    }

    private static void addVertex(SubwayGraph graph, List<Line> lines) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    private static List<Sections> convertToSectionsList(List<Line> lines) {
        return lines.stream()
                .map(Line::getSections)
                .toList();
    }

    private static void addEdgeAndWeight(SubwayGraph graph, List<Sections> allSections, PathType pathType) {
        allSections.forEach(sections -> {
            for (int i = 0; i < sections.size(); i++) {
                graph.addEdgeAndWeight(sections.get(i), pathType);
            }
        });
    }
}
