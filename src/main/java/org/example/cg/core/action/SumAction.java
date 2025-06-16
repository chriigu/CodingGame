package org.example.cg.core.action;

import org.example.cg.core.action.enums.ActionIdentifierEnum;

import java.util.List;

public class SumAction implements Action {

    @Override
    public ActionIdentifierEnum getIdentifier() {
        return ActionIdentifierEnum.SUM;
    }

    @Override
    public List<Double> execute(final List<Double> input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }

        double result = 0;
        for(Double value : input) {
            result += value;
        }

        return List.of(result);
    }
}
