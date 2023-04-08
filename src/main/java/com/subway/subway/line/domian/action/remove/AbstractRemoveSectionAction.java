package com.subway.subway.line.domian.action.remove;

import com.subway.subway.common.exception.CanNotRemoveSectionException;
import com.subway.subway.line.domian.Sections;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractRemoveSectionAction implements SectionRemoveAction {

    protected final Sections sections;

    @Override
    public void validate() {
        if (sections.size() <= 1) {
            throw new CanNotRemoveSectionException();
        }
    }
}
