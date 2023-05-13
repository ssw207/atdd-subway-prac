package com.subway.line.domian;

import lombok.RequiredArgsConstructor;
import org.jgrapht.graph.DefaultWeightedEdge;

@RequiredArgsConstructor
public class SectionEdge extends DefaultWeightedEdge {

    private final Section section;

    public static SectionEdge of(Section section) {
        return new SectionEdge(section);
    }

    public int getDistance() {
        return section.getDistance();
    }

    public int getDuration() {
        return section.getDuration();
    }
}
