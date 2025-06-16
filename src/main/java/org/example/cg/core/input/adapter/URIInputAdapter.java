package org.example.cg.core.input.adapter;

import org.example.cg.core.dto.ProcessParamsDto;

public class URIInputAdapter implements InputAdapter {

    @Override
    public String readInput(final ProcessParamsDto processParamsDto) {
        // TODO read from URI
        String uri = processParamsDto.valueSource();
        System.err.println("URI in not yet implemented");
        return "";
    }
}
