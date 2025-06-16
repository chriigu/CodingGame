package org.example.cg.core.output.writer;

import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.validator.JSONValidator;

import java.util.List;
import java.util.StringJoiner;

public class JSONWriter implements OutputWriter {

    private final JSONValidator validator;

    public JSONWriter() {
        this(new JSONValidator());
    }

    public JSONWriter(final JSONValidator validator) {
        this.validator = validator;
    }

    @Override
    public String writeOutputString(List<Double> valuesToWrite, ProcessParamsDto processParamsDto) {
        StringBuilder sb = new StringBuilder();
        switch (processParamsDto.action().getIdentifier()) {
            case SUM -> {
                if (valuesToWrite == null || valuesToWrite.isEmpty()) {
                    return "\n";
                }

                sb.append("{ \"sum\": ");
                sb.append(valuesToWrite.getFirst());
                sb.append("}");
            }
            case MIN_MAX -> {
                if (valuesToWrite == null || valuesToWrite.isEmpty()) {
                    return "\n";
                }

                sb.append("{ \"min\": ");
                sb.append(valuesToWrite.getFirst());
                sb.append(", \"max\": ");
                sb.append(valuesToWrite.getLast());
                sb.append("}");
            }
            case LT4 -> {
                if (valuesToWrite == null || valuesToWrite.isEmpty()) {
                    return "[]\n";
                }

                StringJoiner sj = new StringJoiner(",");
                sb.append("[");
                valuesToWrite.forEach(value -> sj.add(String.valueOf(value)));
                sb.append(sj);
                sb.append("]");
            }
        }

        String output = sb.toString();
        validator.validateString(output);
        return output;
    }
}
