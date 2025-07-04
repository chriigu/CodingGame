package org.example.cg.core.output.adapter;

import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.output.enums.ExitCodeEnum;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileOutputAdapter implements OutputAdapter {

    @Override
    public void writeResultString(final String outputString, final ProcessParamsDto processParamsDto) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(processParamsDto.outputDestination())))) {
            writer.write(outputString);
        } catch (IOException e) {
            throw new CodingGameException(ExitCodeEnum.WRITE_ERROR.getExitCode(), "Error writing file: " + e.getMessage(), e);
        }
    }
}
