package org.example.cg.core.action;

import java.util.List;

public class SumAction implements Action {

    @Override
    public List<Double> execute(final List<Double> input) {

        double result = 0;
        for(Double value : input) {
            result += value;
        }

        return List.of(result);
    }
}
