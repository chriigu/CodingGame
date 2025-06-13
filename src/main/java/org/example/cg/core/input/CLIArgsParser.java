package org.example.cg.core.input;

import org.apache.commons.cli.*;
import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.input.enums.InputParamIdentifierEnum;
import org.example.cg.core.output.enums.ReturnCodeEnum;

public class CLIArgsParser {

    private Options options;

    public CLIArgsParser() {
        options = new Options();
        for (InputParamIdentifierEnum paramIdentifier : InputParamIdentifierEnum.values()) {
            options.addOption(paramIdentifier.getParamIdentifier(), paramIdentifier.hasArg(), paramIdentifier.getParamDesc());
        }
    }

    public ProcessParamsDto parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String value = cmd.getArgList().getFirst();
            throw new CodingGameException(ReturnCodeEnum.OK.getExitCode(), "values: " + value);
        } catch (ParseException e) {
            throw new CodingGameException(ReturnCodeEnum.FORMAT_ERROR.getExitCode(), "Error parsing arguments", e);
        }
    }
}
