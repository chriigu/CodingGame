package org.example.cg;

import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.input.CLIArgsParser;
import org.example.cg.core.output.enums.ExitCodeEnum;

import java.util.List;

public class CodingGameApp {

    public int runApp(String[] args) throws CodingGameException {
        CLIArgsParser parser = new CLIArgsParser();
        ProcessParamsDto processParamsDto = parser.parse(args);
        if (processParamsDto == null) {
            return ExitCodeEnum.OK.getExitCode();
        }

        String inputString = processParamsDto.inputAdapter().readInput(processParamsDto);
        List<Double> valuesToProcess = processParamsDto.inputReader().readInputString(inputString);
        List<Double> resultList = processParamsDto.actions().getFirst().execute(valuesToProcess);
        String outputString = processParamsDto.outputWriter().writeOutputString(resultList);
        processParamsDto.outputAdapter().writeResultString(outputString);

        return ExitCodeEnum.OK.getExitCode();
    }
}
