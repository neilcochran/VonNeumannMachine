package com.neilcochran.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for BitUtil's helper functions
 */
public class BitUtilTests {

    /**
     * Test valid bit length calculations
     * @param expectedBitLength The expected bit length
     * @param n The input integer to calculate the bit length of
     */
    @ParameterizedTest(name = "Calculate the bit length of {1}")
    @CsvFileSource(resources = {"/bitLengths.csv"}, numLinesToSkip = 1)
    void testValidBitLengthCalculations(int expectedBitLength,int n) {
        assertEquals(expectedBitLength, BitUtils.getBitLength(n));
    }

    /**
     * Test passing invalid (negative) integers to BitUtil.getBitLength() and ensure it throws an IllegalArgumentException
     * @param n The negative input integer
     */
    @ParameterizedTest(name = "Pass invalid (negative) integer: {0} to BitUtil.getBitLength() and ensure it throws an IllegalArgumentException")
    @CsvFileSource(resources = "/invalidBitLengthInputs.csv")
    void testInvalidBitLengthCalculations(int n) {
        assertThrows(IllegalArgumentException.class, () -> BitUtils.getBitLength(n));
    }
}

