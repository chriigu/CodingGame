package org.example.cg.core.output.adapter;

import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.output.enums.ExitCodeEnum;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Test similar to FileOutputAdapter
 * Test with connection issues
 *
 */
public class URIOutputAdapter implements OutputAdapter {

    @Override
    public void writeResultString(final String outputString, final ProcessParamsDto processParamsDto) {
        // TODO implement
        System.err.println("URI out not yet implemented");
        String destination = processParamsDto.outputDestination();
    }
}
