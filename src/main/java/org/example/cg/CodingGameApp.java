package org.example.cg;

import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.input.CLIArgsParser;
import org.example.cg.core.output.enums.ExitCodeEnum;

public class CodingGameApp {

    public int runApp(String[] args) throws CodingGameException {
        CLIArgsParser parser = new CLIArgsParser();
        ProcessParamsDto processParamsDto = parser.parse(args);
        if (processParamsDto == null) {
            return ExitCodeEnum.OK.getExitCode();
        }

        return ExitCodeEnum.OK.getExitCode();
    }
}
