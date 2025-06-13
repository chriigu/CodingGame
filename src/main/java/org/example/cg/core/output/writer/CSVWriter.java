package org.example.cg.core.output.writer;

import org.example.cg.core.validator.CSVValidator;

import java.util.List;
import java.util.StringJoiner;

public class CSVWriter implements OutputWriter {

    private final CSVValidator validator;

    public CSVWriter() {
        this.validator = new CSVValidator();
    }

    public CSVWriter(CSVValidator validator) {
        this.validator = validator;
    }

    @Override
    public String writeOutputString(List<Double> valuesToWrite) {
        if(valuesToWrite == null || valuesToWrite.isEmpty()) {
            return "\n";
        }

        StringJoiner joiner = new StringJoiner(",");
        for (Double value : valuesToWrite) {
            joiner.add(value.toString());
        }
        String outputString = joiner + "\n";
        validator.validateString(outputString);
        return outputString;
    }
}
