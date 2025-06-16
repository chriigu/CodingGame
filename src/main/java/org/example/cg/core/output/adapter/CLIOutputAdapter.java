package org.example.cg.core.output.adapter;

import org.example.cg.core.dto.ProcessParamsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLIOutputAdapter implements OutputAdapter {

    private static final Logger log = LoggerFactory.getLogger(CLIOutputAdapter.class);


    @Override
    public void writeResultString(final String outputString, final ProcessParamsDto processParamsDto) {
        log.info(outputString);
    }
}
