package com.neilcochran.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for BitUtil's helper functions
 */
public class BitUtilTests {

    /**
     * Test bit length calculation
     * @param n The integer to calculate the bit length of
     * @param expectedLength The expected bit length result
     */
    @CsvFileSource(resources = {"/bit-csv/bitLengths.csv"}, numLinesToSkip = 1)
    @ParameterizedTest(name = "testing bit length calculation of {0} expected length is {1}")
    public void bitLengthTest(int n, int expectedLength) {
        assertEquals(expectedLength, BitUtils.getBitLength(n));
    }
}