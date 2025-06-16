package org.example.cg.core.input.adapter;

import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.output.enums.ExitCodeEnum;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileInputAdapter implements InputAdapter {

    @Override
    public String readInput(final ProcessParamsDto processParamsDto) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(processParamsDto.valueSource())))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new CodingGameException(ExitCodeEnum.READ_ERROR.getExitCode(), "Error reading file: " + e.getMessage(), e);
        }
        return sb.toString();
    }
}
