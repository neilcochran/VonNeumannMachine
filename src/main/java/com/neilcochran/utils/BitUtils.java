package com.neilcochran.utils;

/**
 * A collection of static helper utilities
 */
public class BitUtils {

    /**
     * Calculates the number of bits needed to represent a positive integer.
     * Calculate log2(n) indirectly using log rules or return 1 if the input is 0
     * @param n The input integer
     * @return The number of bits needed to represent the input integer n
     * @throws IllegalArgumentException If the input integer is negative
     */
    public static int getBitLength(int n) {
        if(n < 0) {
            throw new IllegalArgumentException("input integer cannot be negative");
        }
        return n == 0 ? 1 : (int)((Math.log(n) / Math.log(2))) + 1;
    }

    /**
     * Check if a given int exceeds the given maxBitLength when interpreting it in binary
     * @param toValidate The int whose bit length is to be validated
     * @param maxBitLength The maximum allowed number of bits
     * @return True if the int uses maxBitLength or fewer bits, false if it requires more bits than maxBitLength
     */
    public static boolean validateBitLength(int toValidate, int maxBitLength) {
        return getBitLength(toValidate) <= maxBitLength;
    }

    /**
     * Returns the Kth bit of n
     * @param n The integer input
     * @param k The bit index to retrieve
     * @return The Kth bit of n
     * @throws IllegalArgumentException if k is negative or greater than the number of bits in n
     */
    public static int getKthBit(int n, int k) {
        if(k < 0 || k > (getBitLength(n) - 1)) {
            throw new IllegalArgumentException(String.format("Invalid bit index: %d requested for input: %d", k, n));
        }
        //Shift the relevant bit all the way to the right and compare it to a constant bit mask of 1
        return (n >> k) & 1;
    }

    /**
     * For a given integer n return the int value of just the bits of n between [start, end]
     * @param n The integer to get the bit range value from
     * @param start The index of the bit at the start of the range (inclusive)
     * @param end The index of the bit at the end of the range (inclusive)
     * @return The integer value of just the bits of n between [start, end]
     * @throws IllegalArgumentException if the bit range is invalid for the given input
     */
    public static int getBitRange(int n, int start, int end) {
        var bitLength = getBitLength(n);
        if(start < 0 || end < 0 || start > end || end > bitLength) {
            throw new IllegalArgumentException(String.format("Invalid bit range: [%d, %d] for input: %d", start, end, n));
        }
        var result = 0;
        for(int k = 0, i = start; i <= end; i++, k++) {
            //get the bit at the current index. If it's set add 2^k to the result
            result += getKthBit(n, i) == 1
                    ? 1 << k //right bit shift to get 2^k
                    : 0;
        }
        return result;
    }

    /**
     * Return a string representing the binary sequence of the input integer
     * @param n The input integer to generate the binary string from
     * @return A String representing the binary value of the input integer
     */
    public static String getBinaryString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for(var i = 0; i <= getBitLength(n); i++) {
            stringBuilder.append(getKthBit(n, i));
        }
        return stringBuilder.toString();
    }
}