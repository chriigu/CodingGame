package org.example.cg;

import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.input.CLIArgsParser;
import org.example.cg.core.output.enums.ExitCodeEnum;

import java.util.List;

public class CodingGameApp {

    private final CLIArgsParser parser;

    public CodingGameApp() {
        this(new CLIArgsParser());
    }

    public CodingGameApp(final CLIArgsParser parser) {
        this.parser = parser;
    }

    public int runApp(final String[] args) throws CodingGameException {
        ProcessParamsDto processParamsDto = parser.parse(args);
        if (processParamsDto == null) {
            return ExitCodeEnum.OK.getExitCode();
        }

        String inputString = processParamsDto.inputAdapter().readInput(processParamsDto);
        List<Double> valuesToProcess = processParamsDto.inputReader().readInputString(inputString);
        List<Double> resultList = processParamsDto.action().execute(valuesToProcess);
        String outputString = processParamsDto.outputWriter().writeOutputString(resultList, processParamsDto);
        processParamsDto.outputAdapter().writeResultString(outputString, processParamsDto);

        return ExitCodeEnum.OK.getExitCode();
    }
}
