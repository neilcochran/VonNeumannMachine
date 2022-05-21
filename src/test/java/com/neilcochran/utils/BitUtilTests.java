package com.neilcochran.utils;

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
     * Test valid bit length calculations
     * @param expectedBitLength The expected bit length
     * @param n The input integer to calculate the bit length of
     */
    @ParameterizedTest(name = "Calculate the bit length of {1}")
    @CsvFileSource(resources = {"/validBitLengths.csv"}, numLinesToSkip = 1)
    void testValidBitLengthCalculations(int expectedBitLength,int n) {
        assertEquals(expectedBitLength, BitUtils.getBitLength(n));
    }

    /**
     * Test passing invalid (negative) integers to BitUtil.getBitLength() and ensure it throws an IllegalArgumentException
     * @param n The negative input integer
     */
    @ParameterizedTest(name = "Pass invalid (negative) integer: {0} to BitUtil.getBitLength() and ensure it throws an IllegalArgumentException")
    @CsvFileSource(resources = "/invalidBitLengthInputs.csv", numLinesToSkip = 1)
    void testInvalidBitLengthCalculations(int n) {
        assertThrows(IllegalArgumentException.class, () -> BitUtils.getBitLength(n));
    }

    /**
     * Test passing valid integers and max bit lengths to BitUtil.validateBitLength()
     * @param maxBitLength The maximum number of bits the input number can use
     * @param toCheck The number whose bit length will be checked
     */
    @ParameterizedTest(name = "Pass valid input: {1} and max bit length: {0} to BitUtil.validateBitLength()")
    @CsvFileSource(resources = "/validBitLengths.csv", numLinesToSkip = 1)
    void testValidBitLengthChecks(int maxBitLength, int toCheck) {
        assertTrue(BitUtils.validateBitLength(toCheck, maxBitLength));
    }

    /**
     * Test passing invalid (too big) integers and max bit lengths to BitUtil.validateBitLength()
     * @param maxBitLength The maximum number of bits the input number can use
     * @param toCheck The number whose bit length will be checked
     */
    @ParameterizedTest(name = "Pass invalid (too big) input {1} and max bit length: {0} to BitUtil.validateBitLength()")
    @CsvFileSource(resources = "/invalidBitLengths.csv", numLinesToSkip = 1)
    void testInvalidBitLengthChecks(int maxBitLength, int toCheck) {
        assertFalse(BitUtils.validateBitLength(toCheck, maxBitLength));
    }

    /**
     * Test retrieving a valid bit by its index
     * @param actualBitValue The actual value of the bit at the given index
     * @param n The input integer
     * @param bitIndex The index of the bit to retrieve
     */
    @ParameterizedTest(name = "Test retrieving the bit at index: {2} of {1} using BitUtil.getKthBit()")
    @CsvFileSource(resources = "/validBitIndexRetrieval.csv", numLinesToSkip = 1)
    void testValidBitRetrieval(int actualBitValue, int n, int bitIndex) {
        assertEquals(actualBitValue, BitUtils.getKthBit(n, bitIndex));
    }
}

