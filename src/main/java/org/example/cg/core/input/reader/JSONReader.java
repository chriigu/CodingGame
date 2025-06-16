package org.example.cg.core.input.reader;

import org.example.cg.core.validator.JSONValidator;

import java.util.Collections;
import java.util.List;

public class JSONReader implements InputReader{

    private final JSONValidator validator;

    public JSONReader() {
        this(new JSONValidator());
    }

    public JSONReader(final JSONValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<Double> readInputString(final String input) {
        // TODO implement
        return Collections.emptyList();
    }
}
