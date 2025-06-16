package org.example.cg.core.input.reader;

import org.example.cg.core.validator.CSVValidator;

import java.util.Arrays;
import java.util.List;

public class CSVReader implements InputReader{

    private final CSVValidator validator;

    public CSVReader() {
        this(new CSVValidator());
    }

    public CSVReader(CSVValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<Double> readInputString(String input) {
        validator.validateString(input);

        String trimmedInput = input.replace(" ", "")
                .replace("\t", "")
                .replace("\n", "")
                .replace("\r", "");

        String[] values = trimmedInput.split(",");

        return Arrays.stream(values)
                .map(Double::parseDouble)
                .toList();
    }
}
