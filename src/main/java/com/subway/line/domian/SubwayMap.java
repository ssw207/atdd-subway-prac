package com.subway.line.domian;

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

        return graph.createShortestPath(source, target);
    }

    private SubwayGraph createSubwayGraph(PathType pathType) {
        SubwayGraph graph = new SubwayGraph();

        // 정점 추가
        addVertex(graph);

        // 간선 추가
        addEdgeAndWeight(graph, pathType);

        return graph;
    }

    private void addVertex(SubwayGraph graph) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    private void addEdgeAndWeight(SubwayGraph graph, PathType pathType) {
        lines.stream()
                .flatMap(s1 -> s1.getSections().stream())
                .forEach(s -> graph.addEdgeAndWeight(s, pathType));
    }
}
