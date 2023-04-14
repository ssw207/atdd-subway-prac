package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import lombok.RequiredArgsConstructor;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

@RequiredArgsConstructor
public class PathFinder {

    private final WeightedMultigraph<Station, SectionEdge> graph;

    public static PathFinder of(List<Line> lines) {
        return new PathFinder(createGraph(lines));
    }

    private static WeightedMultigraph<Station, SectionEdge> createGraph(List<Line> lines) {
        WeightedMultigraph<Station, SectionEdge> graph = new WeightedMultigraph<>(SectionEdge.class);

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

    private static void addEdge(WeightedMultigraph<Station, SectionEdge> graph, List<Sections> allSections) {
        allSections.forEach(sections -> {
            for (int i = 0; i < sections.size(); i++) {
                addSectionToGraphByEdge(graph, sections.get(i));
            }
        });
    }

    private static void addSectionToGraphByEdge(WeightedMultigraph<Station, SectionEdge> graph, Section section) {
        SectionEdge edge = SectionEdge.of(section);

        graph.addEdge(
                section.getUpStation(),
                section.getDownStation(),
                edge);

        graph.setEdgeWeight(edge, section.getDistance());
    }

    private static void addVertex(WeightedMultigraph<Station, SectionEdge> graph, List<Line> lines) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    public Path findPath(long source, long target) {
        GraphPath<Station, SectionEdge> pathResult = new DijkstraShortestPath<>(graph)
                .getPath(
                        Station.of(source),
                        Station.of(target));

        return Path.of(
                (int) pathResult.getWeight(),
                pathResult.getVertexList());
    }
}
