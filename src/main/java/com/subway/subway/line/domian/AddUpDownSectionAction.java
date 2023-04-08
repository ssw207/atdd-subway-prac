package com.subway.subway.line.domian;

public class AddUpDownSectionAction extends AbstractSectionAddAction {

    public AddUpDownSectionAction(Sections sections, Section addSectionTarget) {
        super(sections, addSectionTarget);
    }

    @Override
    public void add() {
        sections.forceAdd(newSection);
    }
}
