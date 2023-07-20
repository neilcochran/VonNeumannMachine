package com.neilcochran.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
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
        var n = 1024;
        assertTrue(BitUtils.validateBitLength(n, 11));
        assertFalse(BitUtils.validateBitLength(n, 10));
    }

    /**
     * Test bit retrieval using BitUtils.getKthBit()
     */
    @Test
    public void getKthBitTest() {
        var n = 6;
        assertEquals(0, BitUtils.getKthBit(n, 0));
        assertEquals(1, BitUtils.getKthBit(n, 1));
        assertEquals(1, BitUtils.getKthBit(n, 2));
    }

    /**
     * Test clearing specific bits using BitUtils.clearKthBit()
     */
    @Test
    public void clearKthBitTest() {
        var n = 7;
        assertEquals(6, BitUtils.clearKthBit(n, 0));
        assertEquals(5, BitUtils.clearKthBit(n, 1));
        assertEquals(3, BitUtils.clearKthBit(n, 2));
    }

    /**
     * Test setting specific bits using BitUtils.setKthBit()
     */
    @Test
    public void setKthBitTest() {
        var n = BitUtils.setKthBit(0, 0);
        assertEquals(1, n);
        n = BitUtils.setKthBit(n, 1);
        assertEquals(3, n);
        n = BitUtils.setKthBit(n, 2);
        assertEquals(7, n);
    }

    /**
     * Test bit rotation calculations using BitUtils.calculateRotate()
     */
    @Test
    public void calculateRotateTest() {
        assertEquals(0, BitUtils.calculateRotate(0, 0));
        assertEquals(0, BitUtils.calculateRotate(1, 0));
        assertEquals(1, BitUtils.calculateRotate(0, 1));
        assertEquals(448, BitUtils.calculateRotate(13, 7));
        assertEquals(67108864, BitUtils.calculateRotate(4, 4));
    }

    /**
     * Test validating bit ranges using BitUtils.isValidBitRange()
     */
    @Test
    public void isValidBitRangeTest() {
        assertTrue(BitUtils.isValidBitRange(0, 0));
        assertTrue(BitUtils.isValidBitRange(0, 1));
        assertTrue(BitUtils.isValidBitRange(4, 31));
        assertFalse(BitUtils.isValidBitRange(1, 0));
        assertFalse(BitUtils.isValidBitRange(-1, 0));
        assertFalse(BitUtils.isValidBitRange(1, -1));
    }

    /**
     * Test retrieving bit ranges using BitUtils.getBitRange(n, start, end)
     */
    @Test
    public void getBitRangeTest() {
        var n = 15;
        assertEquals(15, BitUtils.getBitRange(n, 0, 3));
        assertEquals(7, BitUtils.getBitRange(n, 0, 2));
        assertEquals(3, BitUtils.getBitRange(n, 0, 1));
        assertEquals(1, BitUtils.getBitRange(n, 0, 0));
    }

    /**
     * Test retrieving bit ranges using BitUtils.getBitRange(n, bitRange)
     */
    @Test
    public void getBitRangeObjectTest() {
        var n = 15;
        assertEquals(15, BitUtils.getBitRange(n, new BitRange(0, 3)));
        assertEquals(7, BitUtils.getBitRange(n, new BitRange(0, 2)));
        assertEquals(3, BitUtils.getBitRange(n, new BitRange(0, 1)));
        assertEquals(1, BitUtils.getBitRange(n, new BitRange(0, 0)));
    }

    /**
     * Test retrieval of binary strings using BitUtils.getBinaryString() for varying data sizes both with and without left padding
     */
    @Test
    public void getBinaryStringTest() {
        assertEquals("00000000", BitUtils.getBinaryString(0, DataSize.BYTE, true));
        assertEquals("0", BitUtils.getBinaryString(0, DataSize.BYTE, false));
        assertEquals("0000000000000110", BitUtils.getBinaryString(6, DataSize.HALF_WORD, true));
        assertEquals("110", BitUtils.getBinaryString(6, DataSize.HALF_WORD, false));
        assertEquals("00000000000000000000000001100100", BitUtils.getBinaryString(100, DataSize.WORD, true));
        assertEquals("1100100", BitUtils.getBinaryString(100, DataSize.WORD, false));
    }

    /**
     * Test parsing a single bit into a boolean using BitUtils.bitToBool().
     * Ensure an error is thrown for any multi bit value
     */
    @Test
    public void bitToBoolTest() {
        assertFalse(BitUtils.bitToBool(0));
        assertTrue(BitUtils.bitToBool(1));
        assertThrowsExactly(IllegalArgumentException.class, () -> BitUtils.bitToBool(2));
    }
}