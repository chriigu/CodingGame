package org.example.cg.core.output.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLIOutputAdapter implements OutputAdapter {

    private static final Logger log = LoggerFactory.getLogger(CLIOutputAdapter.class);


    @Override
    public void writeResultString(String outputString) {
        log.info(outputString);
    }
}
