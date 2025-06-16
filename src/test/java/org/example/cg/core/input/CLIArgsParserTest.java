package org.example.cg.core.input;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.example.cg.core.action.Action;
import org.example.cg.core.action.LT4Action;
import org.example.cg.core.action.MinMaxAction;
import org.example.cg.core.action.SumAction;
import org.example.cg.core.action.enums.ActionIdentifierEnum;
import org.example.cg.core.dto.ProcessParamsDto;
import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.input.adapter.CLIInputAdapter;
import org.example.cg.core.input.adapter.InputAdapter;
import org.example.cg.core.input.reader.CSVReader;
import org.example.cg.core.input.reader.InputReader;
import org.example.cg.core.output.adapter.CLIOutputAdapter;
import org.example.cg.core.output.adapter.OutputAdapter;
import org.example.cg.core.output.writer.CSVWriter;
import org.example.cg.core.output.writer.OutputWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.cg.core.output.enums.ExitCodeEnum.INPUT_EMPTY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test parseActions for invalid actions and for all valid action arguments
 * Test other methods to parse for things after the lower priority functions have been added similarly with if/else to test the branches and output values
 */
class CLIArgsParserTest {

    private CLIArgsParser cliArgsParser;

    @BeforeEach
    void setUp() {
        cliArgsParser = new CLIArgsParser();
    }

    @Test
    void parseHelp() throws ParseException {
        // given
        Options options = spy(Options.class);
        CommandLineParser parser = mock(CommandLineParser.class);
        CommandLine cmd  = mock(CommandLine.class);
        when(cmd.hasOption("help")).thenReturn(true);
        when(parser.parse(eq(options), any(), eq(true))).thenReturn(cmd);
        cliArgsParser = new CLIArgsParser(options, parser, new HashMap<>());

        // when
        ProcessParamsDto result = cliArgsParser.parse(List.of("-f", "csv", "-F", "csv", "1,2", "-a" , "sum").toArray(new String[0]));

        // then
        assertNull(result);
    }

    @Test
    void parse() throws ParseException {
        // given
        Options options = spy(Options.class);
        CommandLineParser parser = mock(CommandLineParser.class);
        CommandLine cmd  = mock(CommandLine.class);
        when(cmd.getArgList()).thenReturn(List.of("1,2"));
        when(cmd.getOptionValue("a")).thenReturn("sum");
        when(cmd.hasOption("help")).thenReturn(false);
        when(parser.parse(eq(options), any(), eq(true))).thenReturn(cmd);
        cliArgsParser = new CLIArgsParser(options, parser, new HashMap<>(
                Map.of(ActionIdentifierEnum.SUM, new SumAction(),
                        ActionIdentifierEnum.MIN_MAX, new MinMaxAction(),
                        ActionIdentifierEnum.LT4, new LT4Action())
        ));

        // when
        ProcessParamsDto result = cliArgsParser.parse(List.of("-f", "csv", "-F", "csv", "1,2", "-a" , "sum").toArray(new String[0]));

        // then
        assertNotNull(result);
        assertNotNull(result.action());
        assertEquals(SumAction.class, result.action().getClass());
        assertEquals("1,2", result.valueSource());
        assertEquals(CLIInputAdapter.class, result.inputAdapter().getClass());
        assertEquals(CSVReader.class, result.inputReader().getClass());
        assertEquals(CLIOutputAdapter.class, result.outputAdapter().getClass());
        assertEquals(CSVWriter.class, result.outputWriter().getClass());
    }

    @Test
    void parseValueSourceNull() {
        // given
        CommandLine input = null;
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> cliArgsParser.parseValueSource(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());
    }

    @Test
    void parseValueSourceEmptyInput() {
        // given
        CommandLine input = mock(CommandLine.class);
        when(input.getArgList()).thenReturn(Collections.emptyList());
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> cliArgsParser.parseValueSource(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());
    }

    @Test
    void parseValueSource() {
        // given
        CommandLine input = mock(CommandLine.class);
        when(input.getArgList()).thenReturn(List.of("1.2,1"));
        // when
        String result = cliArgsParser.parseValueSource(input);

        // then
        assertNotNull(result);
        assertEquals("1.2,1", result);
    }

    @Test
    void parseInputAdapterNull() {
        // given
        CommandLine input = null;
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> cliArgsParser.parseInputAdapter(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());
    }

    @Test
    void parseInputAdapter() {
        // given
        CommandLine input = mock(CommandLine.class);
        when(input.getOptionValue("i")).thenReturn(null);
        // when
        InputAdapter result = cliArgsParser.parseInputAdapter(input);

        // then
        assertNotNull(result);
        assertEquals(CLIInputAdapter.class, result.getClass());
    }

    @Test
    void parseInputReaderNull() {
        // given
        CommandLine input = null;
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> cliArgsParser.parseInputReader(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());
    }

    @Test
    void parseInputReader() {
        // given
        CommandLine input = mock(CommandLine.class);
        when(input.getOptionValue("f")).thenReturn(null);
        // when
        InputReader result = cliArgsParser.parseInputReader(input);

        // then
        assertNotNull(result);
        assertEquals(CSVReader.class, result.getClass());
    }

    @Test
    void parseOutputAdapterNull() {
        // given
        CommandLine input = null;
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> cliArgsParser.parseOutputAdapter(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());

    }

    @Test
    void parseOutputAdapter() {
        // given
        CommandLine input = mock(CommandLine.class);
        when(input.getOptionValue("o")).thenReturn(null);
        // when
        OutputAdapter result = cliArgsParser.parseOutputAdapter(input);

        // then
        assertNotNull(result);
        assertEquals(CLIOutputAdapter.class, result.getClass());
    }

    @Test
    void parseOutputWriterNull() {
        // given
        CommandLine input = null;
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> cliArgsParser.parseOutputWriter(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());
    }

    @Test
    void parseOutputWriter() {
        // given
        CommandLine input = mock(CommandLine.class);
        when(input.getOptionValue("F")).thenReturn(null);
        // when
        OutputWriter result = cliArgsParser.parseOutputWriter(input);

        // then
        assertNotNull(result);
        assertEquals(CSVWriter.class, result.getClass());
    }

    @Test
    void parseActionNull() {
        // given
        CommandLine input = null;
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> cliArgsParser.parseAction(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());
    }

    @Test
    void parseAction() {
        // given
        CommandLine input = mock(CommandLine.class);
        when(input.getOptionValue("a")).thenReturn("sum");
        // when
        Action result = cliArgsParser.parseAction(input);

        // then
        assertNotNull(result);
        assertEquals(SumAction.class, result.getClass());
    }
}