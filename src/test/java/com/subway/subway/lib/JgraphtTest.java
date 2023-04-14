package com.subway.subway.lib;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("로컬 학습 테스트 용")
public class JgraphtTest {

    @Test
    public void 다익스트라_알고리즘_최단거리_탐색() {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);

        // 최단거리를 탐색할 역 추가
        long station1 = 1L;
        long station2 = 2L;
        long station3 = 3L;

        graph.addVertex(station1);
        graph.addVertex(station2);
        graph.addVertex(station3);

        // 최단 거리르 탐색할 구간 추가
        int distance = 2;
        graph.setEdgeWeight(graph.addEdge(station1, station2), distance);
        graph.setEdgeWeight(graph.addEdge(station2, station3), distance + 1);
        graph.setEdgeWeight(graph.addEdge(station1, station3), distance + 100);

        DijkstraShortestPath<Long, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        List<Long> 최단거리_역_리스트 = dijkstraShortestPath.getPath(station3, station1).getVertexList();

        assertThat(최단거리_역_리스트.size()).isEqualTo(3);
    }
}
