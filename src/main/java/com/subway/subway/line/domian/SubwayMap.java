package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import lombok.RequiredArgsConstructor;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

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
        GraphPath<Station, SectionEdge> pathResult = new DijkstraShortestPath<>(graph.getGraph())
                .getPath(
                        Station.of(source),
                        Station.of(target));

        return Path.of(
                (int) pathResult.getWeight(),
                pathResult.getVertexList());
    }
}
