package com.subway.subway.line.domian;

import com.subway.subway.common.exception.CanNotAddSectionException;

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
        validateCommonAddSection();

        if (newSection.isDistanceLongerThen(savedMiddleSection)) {
            throw new CanNotAddSectionException();
        }
    }

    private void replaceOldMiddleSectionToNew() {
        sections.remove(savedMiddleSection);
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
