package org.example.cg.core.input.adapter;

import org.example.cg.core.dto.ProcessParamsDto;

public class CLIInputAdapter extends AbstractInputAdapter {

    @Override
    public String readInput(ProcessParamsDto processParamsDto) {
        return processParamsDto.valueToProcess();
    }
}
