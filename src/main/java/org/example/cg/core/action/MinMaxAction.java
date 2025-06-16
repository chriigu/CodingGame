package org.example.cg.core.action;

import org.example.cg.core.action.enums.ActionIdentifierEnum;

import java.util.List;

public class MinMaxAction implements Action {

    @Override
    public ActionIdentifierEnum getIdentifier() {
        return ActionIdentifierEnum.MIN_MAX;
    }

    @Override
    public List<Double> execute(final List<Double> input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for(Double value : input) {
            if(value < min) { min = value; }
            if(value > max) { max = value; }
        }

        return List.of(min, max);
    }
}
