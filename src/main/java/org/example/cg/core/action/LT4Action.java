package org.example.cg.core.action;

import java.util.List;

public class LT4Action implements Action {

    @Override
    public List<Double> execute(final List<Double> input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }

        return input.stream()
                .filter(v -> v < 4)
                .toList();
    }
}
