package org.example.cg.core.input.adapter;

import org.example.cg.core.dto.ProcessParamsDto;

public interface InputAdapter {
    String readInput(final ProcessParamsDto processParamsDto);
}
