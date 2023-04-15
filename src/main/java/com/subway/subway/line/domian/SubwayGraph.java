package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class SubwayGraph {

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
        GraphPath<Station, SectionEdge> pathResult = new DijkstraShortestPath<>(graph)
                .getPath(
                        Station.of(source),
                        Station.of(target));

        return Path.of(
                getTotalDistance(pathResult),
                getPathStations(pathResult));
    }

    private List<Station> getPathStations(GraphPath<Station, SectionEdge> pathResult) {
        return pathResult.getVertexList();
    }

    private int getTotalDistance(GraphPath<Station, SectionEdge> pathResult) {
        return (int) pathResult.getWeight();
    }
}
