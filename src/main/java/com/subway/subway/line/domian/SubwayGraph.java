package com.subway.subway.line.domian;

import com.subway.subway.station.domain.Station;
import lombok.Getter;
import org.jgrapht.graph.SimpleWeightedGraph;

@Getter
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
}
