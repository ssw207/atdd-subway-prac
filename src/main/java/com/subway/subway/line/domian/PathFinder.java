package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import lombok.RequiredArgsConstructor;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

@RequiredArgsConstructor
public class PathFinder {

    private final List<Line> lines;

    public static PathFinder of(List<Line> lines) {
        return new PathFinder(lines);
    }

    public Path findPath(long source, long target) {

        WeightedMultigraph<Station, SectionEdge> graph = new WeightedMultigraph<>(SectionEdge.class);

        // 역 추가
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);

        // 탐색할 구간 추가
        List<Sections> allSections = lines.stream()
                .map(Line::getSections)
                .toList();

        allSections.forEach(sections -> {

            for (int i = 0; i < sections.size(); i++) {
                Section section = sections.get(i);
                SectionEdge edge = SectionEdge.of(section);
                graph.addEdge(section.getUpStation(), section.getDownStation(), edge);
                graph.setEdgeWeight(edge, section.getDistance());
            }
        });

        DijkstraShortestPath<Station, SectionEdge> path = new DijkstraShortestPath<>(graph);
        GraphPath<Station, SectionEdge> pathResult = path.getPath(Station.of(source), Station.of(target));

        return Path.of((int) pathResult.getWeight(), pathResult.getVertexList());
    }
}
