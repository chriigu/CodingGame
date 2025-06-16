package org.example.cg.core.output.adapter;

import org.example.cg.core.dto.ProcessParamsDto;

public interface OutputAdapter {

    void writeResultString(final String outputString , final ProcessParamsDto processParamsDto);
}
