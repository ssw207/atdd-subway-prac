package com.subway.subway.line.domian.action.add;

import com.subway.subway.common.exception.section.CanNotAddMiddleSectionExceptionByDistanceLonger;
import com.subway.subway.line.domian.Section;
import com.subway.subway.line.domian.Sections;

public class AddMiddleSectionAction extends AbstractSectionAddAction {

    private final Section savedMiddleSection;

    public AddMiddleSectionAction(Sections sections, Section addSectionTarget, Section savedMiddleSection) {
        super(sections, addSectionTarget);
        this.savedMiddleSection = savedMiddleSection;
    }


    @Override
    public void add() {
        validateAddMiddleSection();
        replaceOldMiddleSectionToNew();
        sections.forceAdd(newSection);
    }

    private void validateAddMiddleSection() {
        super.validate();

        if (newSection.isDistanceLongerThen(savedMiddleSection)) {
            throw new CanNotAddMiddleSectionExceptionByDistanceLonger();
        }
    }

    private void replaceOldMiddleSectionToNew() {
        sections.forceRemove(savedMiddleSection);
        sections.forceAdd(createFixedMiddleSection());
    }

    private Section createFixedMiddleSection() {
        return Section.builder()
                .line(savedMiddleSection.getLine())
                .upStation(newSection.getDownStation())
                .downStation(savedMiddleSection.getDownStation())
                .distance(savedMiddleSection.minusDistance(newSection.getDistance()))
                .build();
    }
}
