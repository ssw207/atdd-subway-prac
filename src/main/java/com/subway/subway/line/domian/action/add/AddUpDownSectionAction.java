package com.subway.subway.line.domian.action.add;

import com.subway.subway.line.domian.Section;
import com.subway.subway.line.domian.Sections;

public class AddUpDownSectionAction extends AbstractSectionAddAction {

    public AddUpDownSectionAction(Sections sections, Section addSectionTarget) {
        super(sections, addSectionTarget);
    }

    @Override
    public void add() {
        validate();
        sections.forceAdd(newSection);
    }
}
