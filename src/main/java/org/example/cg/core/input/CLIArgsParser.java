package org.example.cg.core.input;

import org.apache.commons.cli.*;
import org.example.cg.core.action.Action;
import org.example.cg.core.action.LT4Action;
import org.example.cg.core.action.MinMaxAction;
import org.example.cg.core.action.SumAction;
import org.example.cg.core.action.enums.ActionIdentifierEnum;
import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.input.adapter.CLIInputAdapter;
import org.example.cg.core.input.adapter.FileInputAdapter;
import org.example.cg.core.input.adapter.InputAdapter;
import org.example.cg.core.input.adapter.URIInputAdapter;
import org.example.cg.core.input.enums.InputParamIdentifierEnum;
import org.example.cg.core.input.reader.CSVReader;
import org.example.cg.core.input.reader.InputReader;
import org.example.cg.core.output.adapter.CLIOutputAdapter;
import org.example.cg.core.output.adapter.FileOutputAdapter;
import org.example.cg.core.output.adapter.OutputAdapter;
import org.example.cg.core.output.adapter.URIOutputAdapter;
import org.example.cg.core.output.enums.ExitCodeEnum;
import org.example.cg.core.output.writer.CSVWriter;
import org.example.cg.core.output.writer.JSONWriter;
import org.example.cg.core.output.writer.OutputWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CLIArgsParser {
    private static final Logger log = LoggerFactory.getLogger(CLIArgsParser.class);

    private final Map<ActionIdentifierEnum, Action> actions;

    private final Options options;
    private final CommandLineParser parser;

    private String parsedAction;

    public CLIArgsParser() {
        this(new Options(),
                new DefaultParser(),
                new HashMap<>(
                        Map.of(ActionIdentifierEnum.SUM, new SumAction(),
                                ActionIdentifierEnum.MIN_MAX, new MinMaxAction(),
                                ActionIdentifierEnum.LT4, new LT4Action())
                ));
    }

    public CLIArgsParser(final Options options, final CommandLineParser parser, final Map<ActionIdentifierEnum, Action> actionsMap) {
        this.options = options;
        for (InputParamIdentifierEnum paramIdentifier : InputParamIdentifierEnum.values()) {
            options.addOption(paramIdentifier.getParamIdentifier(), paramIdentifier.hasArg(), paramIdentifier.getParamDesc());
        }
        this.parser = parser;
        this.actions = actionsMap;
    }

    public ProcessParamsDto parse(final String[] args) {
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
            String outputDestination = parseOutputDestination(cmd);
            OutputWriter outputWriter = parseOutputWriter(cmd);
            Action action = parseAction(cmd);
            String valueToProcess = parseValueSource(cmd);

            ProcessParamsDto processParamsDto = new ProcessParamsDto(inputAdapter, inputReader, outputAdapter, outputWriter, outputDestination, action, valueToProcess);
            log.info("Parsed data: {}", processParamsDto);
            return processParamsDto;
        } catch (ParseException e) {
            throw new CodingGameException(ExitCodeEnum.INPUT_EMPTY.getExitCode(), "Error parsing arguments", e);
        }
    }

    String parseValueSource(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.INPUT_SOURCE.getParamIdentifier());
        // If input option is default then parse value from CLI
        if (inputAdapterIsDefault(parsedValue)) {
            if (cmd.getArgList().isEmpty()) {
                throw new CodingGameException(ExitCodeEnum.INPUT_EMPTY.getExitCode(), "Error no input value to process");
            }
            parsedValue = cmd.getArgList().getFirst();

        }

        log.info("Parsed value: {}", parsedValue);
        return parsedValue;
    }

    InputAdapter parseInputAdapter(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.INPUT_SOURCE.getParamIdentifier());
        log.info("Parsed input method (-{}): {}", InputParamIdentifierEnum.INPUT_SOURCE.getParamIdentifier(), parsedValue);

        if (inputAdapterIsDefault(parsedValue)) {
            return new CLIInputAdapter();
        } else if (isURI(parsedValue)) {
            return new URIInputAdapter();
        }

        return new FileInputAdapter();
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

        if (outputAdapterIsDefault(parsedValue)) {
            return new CLIOutputAdapter();
        } else if (isURI(parsedValue)) {
            return new URIOutputAdapter();
        }

        return new FileOutputAdapter();
    }

    String parseOutputDestination(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.OUTPUT_DESTINATION.getParamIdentifier());
        log.info("Parsed output destination (-{}): {}", InputParamIdentifierEnum.OUTPUT_DESTINATION.getParamIdentifier(), parsedValue);

        return parsedValue;
    }

    OutputWriter parseOutputWriter(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.OUTPUT_FORMAT.getParamIdentifier());
        log.info("Parsed output format (-{}): {}", InputParamIdentifierEnum.OUTPUT_FORMAT.getParamIdentifier(), parsedValue);

        return parsedValue != null && parsedValue.equals("json") ? new JSONWriter() : new CSVWriter();
    }

    Action parseAction(final CommandLine cmd) {
        checkCommandLine(cmd);

        String parsedValue = cmd.getOptionValue(InputParamIdentifierEnum.ACTION.getParamIdentifier());
        if (parsedValue == null) {
            throw new CodingGameException(ExitCodeEnum.INPUT_EMPTY.getExitCode(), "Action to execute is missing");
        }
        log.info("Parsed action (-{}): {}", InputParamIdentifierEnum.ACTION.getParamIdentifier(), parsedValue);
        return actions.get(ActionIdentifierEnum.getEnum(parsedValue.toLowerCase()));
    }

    private void checkCommandLine(final CommandLine cmd) {
        if (cmd == null) {
            throw new CodingGameException(ExitCodeEnum.INPUT_EMPTY.getExitCode(), "Commandline is empty");
        }
    }

    private boolean inputAdapterIsDefault(String parsedInputAdapter) {
        return parsedInputAdapter == null ||
                parsedInputAdapter.equals("-");
    }

    private boolean outputAdapterIsDefault(String parsedOutputAdapter) {
        return parsedOutputAdapter == null ||
                parsedOutputAdapter.equals("-");
    }

    private boolean isURI(String isURI) {
        if(!isURI.startsWith("http://") && !isURI.startsWith("https://") && !isURI.startsWith("www.")) {
            return false;
        }
        try {
            URI.create(isURI);
            return true;
        } catch (IllegalArgumentException e) {
            // Ignore -> should be a file if it's not a valid URI
        }
        return false;
    }
}
