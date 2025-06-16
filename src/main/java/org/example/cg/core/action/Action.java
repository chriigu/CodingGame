package org.example.cg.core.action;

import org.example.cg.core.action.enums.ActionIdentifierEnum;

import java.util.List;

public interface Action {

    ActionIdentifierEnum getIdentifier();
    List<Double> execute(final List<Double> input);
}
