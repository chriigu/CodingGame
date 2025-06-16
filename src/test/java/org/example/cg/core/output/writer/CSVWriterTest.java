package org.example.cg.core.output.writer;

import org.example.cg.core.validator.CSVValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CSVWriterTest {

    private CSVWriter csvWriter;

    @BeforeEach
    void setUp() {
        CSVValidator mockValidator = mock(CSVValidator.class);
        csvWriter = new CSVWriter(mockValidator);
    }

    @Test
    void writeOutputStringNull() {
        // given
        List<Double> input = null;
        // when
        String result = csvWriter.writeOutputString(input);
        // then
        assertEquals("\n", result);
    }

    @Test
    void writeOutputStringEmpty() {
        // given
        List<Double> input = new ArrayList<>();
        // when
        String result = csvWriter.writeOutputString(input);
        // then
        assertEquals("\n", result);
    }


    private static Stream<Arguments> validArgs() {
        return Stream.of(
                Arguments.of(List.of(1.0, 2.0), "1.0,2.0\n"),
                Arguments.of(List.of(-1.2, 3.0, -5.0), "-1.2,3.0,-5.0\n")
        );
    }
    @ParameterizedTest
    @MethodSource("validArgs")
    void writeOutputStringValid(List<Double> input, String expectedOutput) {
        // given
        // when
        String result = csvWriter.writeOutputString(input);
        // then
        assertEquals(expectedOutput, result);
    }
}