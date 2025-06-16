package systemtest.functionality;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Maybe make separate module entirely for systemtesting for better configuration etc.
 *
 * Structure smoke and functionality to make some lightweight testing
 *
 * Testcases for functionality:
 * - Different combinations of csv/json format and data source/destination
 * - All actions
 * - Invalid inputs (empty, invalid format, non-existing values and/or params)
 *
 *  -> assert exitCode, assert output (cmd, file, url)
 *
 * Possible improvements:
 * - Make it platform independent
 * - Make it so you can create csv/json files and use them as test input and test output to test against
 * - Make things like path and OS configurable
 *
 */
class MainSystemTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testMain() {

    }
}