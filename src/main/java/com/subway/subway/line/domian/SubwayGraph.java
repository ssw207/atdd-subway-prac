package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotFindPathException;
import com.subway.subway.common.exception.CanNotFindPathExceptionByNotConnected;
import com.subway.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class SubwayGraph {

    public static final String ERROR_MESSAGE_NOT_CONNECTED = "graph must contain the sink vertex";

    private final SimpleWeightedGraph<Station, SectionEdge> graph = new SimpleWeightedGraph<>(SectionEdge.class);

    public void addVertex(Station station) {
        graph.addVertex(station);
    }

    public void addEdgeAndWeight(Section section) {
        SectionEdge edge = SectionEdge.of(section);
        graph.addEdge(section.getUpStation(), section.getDownStation(), edge);
        graph.setEdgeWeight(edge, section.getDistance());
    }

    public Path getPath(long source, long target) {
        try {

            GraphPath<Station, SectionEdge> pathResult = new DijkstraShortestPath<>(graph)
                    .getPath(
                            Station.of(source),
                            Station.of(target));

            return Path.of(
                    getTotalDistance(pathResult),
                    getPathStations(pathResult));

        } catch (IllegalArgumentException e) {
            if (ERROR_MESSAGE_NOT_CONNECTED.equals(e.getMessage())) {
                throw new CanNotFindPathExceptionByNotConnected();
            }

            throw new CanNotFindPathException(e.getMessage());
        }
    }

    private List<Station> getPathStations(GraphPath<Station, SectionEdge> pathResult) {
        return pathResult.getVertexList();
    }

    private int getTotalDistance(GraphPath<Station, SectionEdge> pathResult) {
        return (int) pathResult.getWeight();
    }

    public boolean notExistsStationId(long stationId) {
        return !graph.containsVertex(Station.of(stationId));
    }
}
