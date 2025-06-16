package org.example.cg.core.dto;

import org.example.cg.core.action.Action;
import org.example.cg.core.input.adapter.InputAdapter;
import org.example.cg.core.input.reader.InputReader;
import org.example.cg.core.output.adapter.OutputAdapter;
import org.example.cg.core.output.writer.OutputWriter;

public record ProcessParamsDto(InputAdapter inputAdapter, InputReader inputReader, OutputAdapter outputAdapter, OutputWriter outputWriter, String outputDestination, Action action, String valueSource) {
}
