package com.subway.subway.line.domian;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddUpDownSectionAction implements SectionAddAction {

    private final Sections sections;
    private final Section addSectionTarget;

    @Override
    public void add() {
        sections.forceAdd(addSectionTarget);
    }
}
