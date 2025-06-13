package org.example.cg.core.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SumActionTest {

    private SumAction action;

    @BeforeEach
    void setUp() {
        action = new SumAction();
    }

    @Test
    void testNull() {
        // given
        List<Double> input = null;
        // when
        List<Double> result = action.execute(input);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testEmpty() {
        // given
        List<Double> input = new ArrayList<>();
        // when
        List<Double> result = action.execute(input);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testPositiveValues() {
        // given
        List<Double> input = new ArrayList<>();
        input.add(1.0);
        input.add(2.0);
        input.add(3.0);
        // when
        List<Double> result = action.execute(input);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(6.0, result.getFirst());
    }

    @Test
    void testNegativeValues() {
        // given
        List<Double> input = new ArrayList<>();
        input.add(-1.0);
        input.add(-2.0);
        input.add(-3.0);
        // when
        List<Double> result = action.execute(input);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(-6.0, result.getFirst());
    }

    @Test
    void testPositiveMixedWithNegativeValues() {
        // given
        List<Double> input = new ArrayList<>();
        input.add(1.0);
        input.add(-2.0);
        input.add(3.0);
        // when
        List<Double> result = action.execute(input);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2.0, result.getFirst());
    }
}