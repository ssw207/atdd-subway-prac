package com.subway.line.domian;

import com.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Optional;

public class SubwayGraph {
    private final SimpleWeightedGraph<Station, SectionEdge> graph = new SimpleWeightedGraph<>(SectionEdge.class);

    public void addVertex(Station station) {
        graph.addVertex(station);
    }

    public void addEdge(Section section) {
        SectionEdge edge = SectionEdge.of(section);
        graph.addEdge(section.getUpStation(), section.getDownStation(), edge);
    }

    public void addWeight(Section section, PathType pathType) {
        SectionEdge edge = SectionEdge.of(section);
        graph.setEdgeWeight(edge, pathType.getWeight(section));
    }

    public Optional<Path> getPath(long source, long target) {
        return getPathResult(source, target)
                .map(pathResult -> Path.of(
                        getTotalDistance(pathResult),
                        getTotalDuration(pathResult),
                        getPathStations(pathResult)));
    }

    private Optional<GraphPath<Station, SectionEdge>> getPathResult(long source, long target) {
        GraphPath<Station, SectionEdge> pathResult = new DijkstraShortestPath<>(graph)
                .getPath(
                        Station.of(source),
                        Station.of(target));

        return Optional.ofNullable(pathResult);
    }

    private List<Station> getPathStations(GraphPath<Station, SectionEdge> pathResult) {
        return pathResult.getVertexList();
    }

    private int getTotalDistance(GraphPath<Station, SectionEdge> pathResult) {
        return pathResult.getEdgeList()
                .stream()
                .mapToInt(SectionEdge::getDistance)
                .sum();
    }

    private int getTotalDuration(GraphPath<Station, SectionEdge> pathResult) {
        return pathResult.getEdgeList()
                .stream()
                .mapToInt(SectionEdge::getDuration)
                .sum();
    }

    public boolean notExistsStationId(long stationId) {
        return !graph.containsVertex(Station.of(stationId));
    }
}
