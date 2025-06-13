package org.example.cg.core.input.reader;

import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.validator.CSVValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;


class CSVReaderTest {

    private CSVReader csvReader;
    private CSVValidator mockValidator;

    @BeforeEach
    void setUp() {
        mockValidator = mock(CSVValidator.class);
        csvReader = new CSVReader(mockValidator);
    }

    @Test
    void testInputNull() {
        // given
        String input = null;
        doThrow(new CodingGameException(1, "testerr")).when(mockValidator).validateString(isNull());
        // when
        assertThrows(CodingGameException.class, () -> csvReader.readInputString(input));
        // then
    }

    private static Stream<Arguments> emptyArgs() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("\t"),
                Arguments.of("\n"),
                Arguments.of("\r")
        );
    }

    @ParameterizedTest
    @MethodSource("emptyArgs")
    void testInputEmpty(String inputString) {
        // given
        doThrow(new CodingGameException(1, "testerr")).when(mockValidator).validateString(eq(inputString));
        // when
        assertThrows(CodingGameException.class, () -> csvReader.readInputString(inputString));
        // then
    }

    private static Stream<Arguments> validArgs() {
        return Stream.of(
                Arguments.of("1", 1, 1, 0),
                Arguments.of("-1", 1, -1, 0),
                Arguments.of("2.0", 1, 2, 0),
                Arguments.of("-2.0", 1, -2, 0),
                Arguments.of("1.0,1.0", 2, 1, 1),
                Arguments.of("-2.0,-2.0", 2, -2, -2),
                Arguments.of("2.0,-3.0", 2, 2, -3),
                Arguments.of("-4.0,5.1", 2, -4, 5.1),
                Arguments.of("-12321.1,-4543", 2, -12321.1, -4543)
        );
    }

    @ParameterizedTest
    @MethodSource("validArgs")
    void testInputValid(String inputString, int numberOfValues, double firstValue, double lastValue) {
        // given
        // when
        List<Double> result = csvReader.readInputString(inputString);
        // then
        assertNotNull(result);
        assertEquals(numberOfValues, result.size());
        assertEquals(firstValue, result.getFirst());
        assertEquals(lastValue, result.size() == 2 ? result.getLast() : 0);
    }

}