package org.example.cg.core.output.writer;

import org.example.cg.core.dto.ProcessParamsDto;

import java.util.List;

public interface OutputWriter {

    String writeOutputString(final List<Double> valuesToWrite, ProcessParamsDto processParamsDto);
}
