package org.example.cg.core.input.adapter;

import org.example.cg.core.dto.ProcessParamsDto;

public class CLIInputAdapter implements InputAdapter {

    @Override
    public String readInput(final ProcessParamsDto processParamsDto) {
        return processParamsDto.valueSource();
    }
}
