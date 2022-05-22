package com.neilcochran.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for BitUtil's helper functions
 */
public class BitUtilTests {

    /**
     * Test a valid bit length calculation
     * @param expectedBitLength The expected bit length
     * @param n The input integer to calculate the bit length of
     */
    @ParameterizedTest(name = "Calculate the bit length of {1}")
    @CsvFileSource(resources = {"/bit-csv/validBitLengths.csv"}, numLinesToSkip = 1)
    void testValidBitLengthCalculations(int expectedBitLength,int n) {
        assertEquals(expectedBitLength, BitUtils.getBitLength(n));
    }

    /**
     * Test passing an invalid (negative) integer to BitUtil.getBitLength()
     * @param n The negative input integer
     */
    @ParameterizedTest(name = "Pass invalid (negative) integer: {0} to BitUtil.getBitLength()")
    @CsvFileSource(resources = "/bit-csv/invalidBitLengthInputs.csv", numLinesToSkip = 1)
    void testInvalidBitLengthCalculations(int n) {
        assertThrows(IllegalArgumentException.class, () -> BitUtils.getBitLength(n));
    }

    /**
     * Test passing a valid integer and max bit length to BitUtil.validateBitLength()
     * @param maxBitLength The maximum number of bits the input number can use
     * @param toCheck The number whose bit length will be checked
     */
    @ParameterizedTest(name = "Pass valid input: {1} and max bit length: {0} to BitUtil.validateBitLength()")
    @CsvFileSource(resources = "/bit-csv/validBitLengths.csv", numLinesToSkip = 1)
    void testValidBitLengthCheck(int maxBitLength, int toCheck) {
        assertTrue(BitUtils.validateBitLength(toCheck, maxBitLength));
    }

    /**
     * Test passing an invalid (too big) integer and max bit length to BitUtil.validateBitLength()
     * @param maxBitLength The maximum number of bits the input number can use
     * @param toCheck The number whose bit length will be checked
     */
    @ParameterizedTest(name = "Pass invalid (too big) input {1} and max bit length: {0} to BitUtil.validateBitLength()")
    @CsvFileSource(resources = "/bit-csv/invalidBitLengths.csv", numLinesToSkip = 1)
    void testInvalidBitLengthCheck(int maxBitLength, int toCheck) {
        assertFalse(BitUtils.validateBitLength(toCheck, maxBitLength));
    }

    /**
     * Test retrieving a valid bit by its index
     * @param actualBitValue The actual value of the bit at the given index
     * @param n The input integer
     * @param bitIndex The index of the bit to retrieve
     */
    @ParameterizedTest(name = "Test retrieving the bit at index: {2} of {1} using BitUtil.getKthBit()")
    @CsvFileSource(resources = "/bit-csv/validBitIndexRetrieval.csv", numLinesToSkip = 1)
    void testValidBitRetrieval(int actualBitValue, int n, int bitIndex) {
        assertEquals(actualBitValue, BitUtils.getKthBit(n, bitIndex));
    }

    /**
     * Test trying to retrieve an invalid bit index using BitUtil.getKthBit()
     * @param n The input integer
     * @param bitIndex The index of the bit to retrieve
     */
    @ParameterizedTest(name = "Test retrieving the bit at the invalid index: {1} of {0} using BitUtil.getKthBit()")
    @CsvFileSource(resources = "/bit-csv/invalidBitIndexRetrieval.csv", numLinesToSkip = 1)
    void testInvalidBitRetrieval(int n, int bitIndex) {
        assertThrows(IllegalArgumentException.class, () -> BitUtils.getKthBit(n, bitIndex));
    }

    /**
     * Test retrieving a valid bit range
     * @param actualResult The expected resulting bit range for the given input and range
     * @param n The input integer from which the range will be extracted
     * @param start The start index of the bit range
     * @param end The end index of the bit range
     */
    @ParameterizedTest(name = "Test retrieving the bit range: [{2}, {3}] of: {1}")
    @CsvFileSource(resources = "/bit-csv/validBitRangeRetrieval.csv", numLinesToSkip = 1)
    void testValidBitRangeRetrieval(int actualResult, int n, int start, int end) {
        assertEquals(actualResult, BitUtils.getBitRange(n, start, end));
    }

    /**
     * Test retrieving an invalid bit range
     * @param n The input integer from which the range will be extracted
     * @param start The start index of the bit range
     * @param end The end index of the bit range
     */
    @ParameterizedTest(name = "Test retrieving the invalid bit range: [{1}, {2}] of: {0}")
    @CsvFileSource(resources = "/bit-csv/invalidBitRangeRetrieval.csv", numLinesToSkip = 1)
    void testInvalidBitRangeRetrieval(int n, int start, int end) {
        assertThrows(IllegalArgumentException.class, () -> BitUtils.getBitRange(n, start, end));
    }
}

