package com.subway.line.domian.action.remove;

import com.subway.common.exception.section.CanNotRemoveSectionException;
import com.subway.line.domian.Sections;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractRemoveSectionAction implements SectionRemoveAction {

    protected final Sections sections;

    protected void validate() {
        if (sections.size() <= 1) {
            throw new CanNotRemoveSectionException();
        }
    }
}
