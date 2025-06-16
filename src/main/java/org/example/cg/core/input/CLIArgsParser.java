package org.example.cg.core.input;

import org.apache.commons.cli.*;
import org.example.cg.core.action.Action;
import org.example.cg.core.action.SumAction;
import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.input.adapter.CLIInputAdapter;
import org.example.cg.core.input.adapter.InputAdapter;
import org.example.cg.core.input.enums.InputParamIdentifierEnum;
import org.example.cg.core.input.reader.CSVReader;
import org.example.cg.core.input.reader.InputReader;
import org.example.cg.core.output.adapter.CLIOutputAdapter;
import org.example.cg.core.output.adapter.OutputAdapter;
import org.example.cg.core.output.enums.ExitCodeEnum;
import org.example.cg.core.output.writer.CSVWriter;
import org.example.cg.core.output.writer.OutputWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class CLIArgsParser {
    private static final Logger log = LoggerFactory.getLogger(CLIArgsParser.class);


    private final Options options;
    private final CommandLineParser parser;

    public CLIArgsParser() {
        this(new Options(), new DefaultParser());
    }

    public CLIArgsParser(Options options, CommandLineParser parser) {
        this.options = options;
        for (InputParamIdentifierEnum paramIdentifier : InputParamIdentifierEnum.values()) {
            options.addOption(paramIdentifier.getParamIdentifier(), paramIdentifier.hasArg(), paramIdentifier.getParamDesc());
        }
        this.parser = parser;
    }

    public ProcessParamsDto parse(String[] args) {
        try {
            // stopAtNonOption flag is true so it parses negative floating point numbers correctly
            CommandLine cmd = parser.parse(options, args, true);

            log.info("Recognized options: [{}]", Arrays.toString(cmd.getOptions()));

            if (cmd.hasOption(InputParamIdentifierEnum.HELP.getParamIdentifier())) {
                log.info(InputParamIdentifierEnum.HELP.getParamDesc());
                return null;
            }

            InputAdapter inputAdapter = parseInputAdapter(cmd);
            InputReader inputReader = parseInputReader(cmd);
            OutputAdapter outputAdapter = parseOutputAdapter(cmd);
            OutputWriter outputWriter = parseOutputWriter(cmd);
            List<Action> actions = parseActions(cmd);
            String valueToProcess = parseValueToProcess(cmd);

            ProcessParamsDto processParamsDto = new ProcessParamsDto(inputAdapter, inputReader, outputAdapter, outputWriter, actions, valueToProcess);
            log.info("Parsed data: {}", processParamsDto);
            return processParamsDto;
        } catch (ParseException e) {
            throw new CodingGameException(ExitCodeEnum.INPUT_EMPTY.getExitCode(), "Error parsing arguments", e);
        }
    }

    String parseValueToProcess(CommandLine cmd) {
        checkCommandLine(cmd);

        if(cmd.getArgList().isEmpty()) {
            throw new CodingGameException(ExitCodeEnum.INPUT_EMPTY.getExitCode(), "Error no input value to process");
        }

        String parsedValue = cmd.getArgList().getFirst();
        log.info("Parsed value: {}", parsedValue);
        return parsedValue;
    }

    InputAdapter parseInputAdapter(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.INPUT_SOURCE.getParamIdentifier());
        log.info("Parsed input method (-{}): {}", InputParamIdentifierEnum.INPUT_SOURCE.getParamIdentifier(), parsedValue);
        return new CLIInputAdapter();
    }

    InputReader parseInputReader(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.INPUT_FORMAT.getParamIdentifier());
        log.info("Parsed input format (-{}): {}", InputParamIdentifierEnum.INPUT_FORMAT.getParamIdentifier(), parsedValue);
        return new CSVReader();
    }

    OutputAdapter parseOutputAdapter(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.OUTPUT_DESTINATION.getParamIdentifier());
        log.info("Parsed output method (-{}): {}", InputParamIdentifierEnum.OUTPUT_DESTINATION.getParamIdentifier(), parsedValue);
        return new CLIOutputAdapter();
    }

    OutputWriter parseOutputWriter(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.OUTPUT_FORMAT.getParamIdentifier());
        log.info("Parsed output format (-{}): {}", InputParamIdentifierEnum.OUTPUT_FORMAT.getParamIdentifier(), parsedValue);
        return new CSVWriter();
    }

    List<Action> parseActions(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.ACTION.getParamIdentifier());
        log.info("Parsed action (-{}): {}", InputParamIdentifierEnum.ACTION.getParamIdentifier(), parsedValue);
        return List.of(new SumAction());
    }

    private void checkCommandLine(final CommandLine cmd) {
        if(cmd == null) {
            throw new CodingGameException(ExitCodeEnum.INPUT_EMPTY.getExitCode(), "Commandline is empty");
        }
    }
}
