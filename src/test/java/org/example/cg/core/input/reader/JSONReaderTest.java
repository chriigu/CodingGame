package org.example.cg.core.input.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create tests similarly to CSVReader
 * Null and empty values -> Exception
 * Invalid values -> Exception
 *
 * Single and multiple valid values (negative and positive) -> assert the list is not null, having the right size and containing all the values of the input
 *
 */
class JSONReaderTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void readInputString() {
    }
}