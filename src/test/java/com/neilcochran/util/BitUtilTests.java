package com.neilcochran.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    /**
     * Test BitUtils.validateBitLength() with both a valid and invalid combination
     */
    @Test
    public void validateBitLengthTest() {
        int n = 1024;
        assertTrue(BitUtils.validateBitLength(n, 11));
        assertFalse(BitUtils.validateBitLength(n, 10));
    }

    /**
     * Test bit retrieval using BitUtils.getKthBit()
     */
    @Test
    public void getKthBitTest() {
        int n = 6;
        assertEquals(0, BitUtils.getKthBit(n, 0));
        assertEquals(1, BitUtils.getKthBit(n, 1));
        assertEquals(1, BitUtils.getKthBit(n, 2));
    }

    /**
     * Test clearing specific bits using BitUtils.clearKthBit()
     */
    @Test
    public void clearKthBitTest() {
        int n = 7;
        assertEquals(6, BitUtils.clearKthBit(n, 0));
        assertEquals(5, BitUtils.clearKthBit(n, 1));
        assertEquals(3, BitUtils.clearKthBit(n, 2));
    }

    /**
     * Test setting specific bits using BitUtils.setKthBit()
     */
    @Test
    public void setKthBitTest() {
        int n = BitUtils.setKthBit(0, 0);
        assertEquals(1, n);
        n = BitUtils.setKthBit(n, 1);
        assertEquals(3, n);
        n = BitUtils.setKthBit(n, 2);
        assertEquals(7, n);
    }
}