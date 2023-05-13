package com.subway.line.domian;

import com.subway.common.exception.path.CanNotFindPathExceptionByNotConnected;
import com.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.List;
import java.util.function.ToIntFunction;

public class SubwayGraph {
    private final SimpleDirectedWeightedGraph<Station, SectionEdge> graph = new SimpleDirectedWeightedGraph<>(SectionEdge.class);

    public void addVertex(Station station) {
        graph.addVertex(station);
    }

    public void addEdgeAndWeight(Section section, PathType pathType) {
        SectionEdge edge = SectionEdge.of(section);
        graph.addEdge(section.getUpStation(), section.getDownStation(), edge);
        graph.setEdgeWeight(edge, pathType.getWeight(section));
    }

    public Path createShortestPath(long source, long target) {
        GraphPath<Station, SectionEdge> result = getPathResult(source, target);

        return Path.of(
                getTotalDistance(result),
                getTotalDuration(result),
                getPathStations(result));
    }

    private GraphPath<Station, SectionEdge> getPathResult(long source, long target) {
        GraphPath<Station, SectionEdge> pathResult = new DijkstraShortestPath<>(graph)
                .getPath(Station.of(source), Station.of(target));

        if (pathResult == null) {
            throw new CanNotFindPathExceptionByNotConnected();
        }

        return pathResult;
    }

    private int getTotalDistance(GraphPath<Station, SectionEdge> pathResult) {
        return sum(pathResult, SectionEdge::getDistance);
    }

    private int getTotalDuration(GraphPath<Station, SectionEdge> pathResult) {
        return sum(pathResult, SectionEdge::getDuration);
    }

    private int sum(GraphPath<Station, SectionEdge> pathResult, ToIntFunction<SectionEdge> getDuration) {
        return pathResult.getEdgeList()
                .stream()
                .mapToInt(getDuration)
                .sum();
    }

    private List<Station> getPathStations(GraphPath<Station, SectionEdge> pathResult) {
        return pathResult.getVertexList();
    }

    public boolean notExistsStationId(long stationId) {
        return !graph.containsVertex(Station.of(stationId));
    }
}
