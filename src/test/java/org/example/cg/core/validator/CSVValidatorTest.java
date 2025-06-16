package org.example.cg.core.validator;

import org.example.cg.core.exception.CodingGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.cg.core.output.enums.ExitCodeEnum.FORMAT_ERROR;
import static org.example.cg.core.output.enums.ExitCodeEnum.INPUT_EMPTY;
import static org.junit.jupiter.api.Assertions.*;


class CSVValidatorTest {

    private CSVValidator csvValidator;

    @BeforeEach
    void setUp() {
        csvValidator = new CSVValidator();
    }

    @Test
    void validateStringNull() {
        // given
        String input = null;
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> csvValidator.validateString(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());
    }

    @Test
    void validateStringEmpty() {
        // given
        String input = " \t\n\r";
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> csvValidator.validateString(input));
        // then
        assertEquals(INPUT_EMPTY.getExitCode(), result.getErrorCode());
    }

    @Test
    void validateStringInvalidValue() {
        // given
        String input = "a";
        // when
        CodingGameException result = assertThrows(CodingGameException.class, () -> csvValidator.validateString(input));
        // then
        assertEquals(FORMAT_ERROR.getExitCode(), result.getErrorCode());
    }

    private static Stream<Arguments> validArgs() {
        return Stream.of(
                Arguments.of("1"),
                Arguments.of("-1"),
                Arguments.of("2.0"),
                Arguments.of("-2.0"),
                Arguments.of("1.0,1.0"),
                Arguments.of("-2.0,-2.0"),
                Arguments.of("2.0,-3.0"),
                Arguments.of("-4.0,5.1"),
                Arguments.of("-12321.1,-4543")
        );
    }

    @ParameterizedTest
    @MethodSource("validArgs")
    void testInputValid(String inputString) {
        // given
        // when
        assertDoesNotThrow(() -> csvValidator.validateString(inputString));
    }
}