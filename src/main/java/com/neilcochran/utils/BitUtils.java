package com.neilcochran.utils;

/**
 * A collection of static helper utilities
 */
public class BitUtils {

    /**
     * Calculates the number of bits needed to represent the integer
     * @param n The input integer
     * @return The number of bits needed to represent the input integer n
     */
    public static int getBitLength(int n) {
        //calculate "log base 2 of (toValidate)" indirectly by using log rules
        return (int)(Math.log(n) / Math.log(2));
    }

    /**
     * Check if a given int exceeds the given bitLength when interpreting it in binary
     * @param toValidate The int whose bit length is to be validated
     * @param bitLength The maximum allowed number of bits
     * @return True if the int uses bitLength or less bits, false if it requires more bits than bitLength
     */
    public static boolean validateBitLength(int toValidate, int bitLength) {
        return getBitLength(toValidate) <= bitLength;
    }

    /**
     * For a given integer n return the int value of just the bits of n between [start, end]
     * @param n The integer to get the bit range value from
     * @param start The index of the bit at the start of the range
     * @param end The index of the bit at the end of the range
     * @return The integer value of just the bits of n between [start, end]
     * @throws IllegalArgumentException if the bit range is invalid for the given input
     */
    public static int getBitRange(int n, int start, int end) {
        var bitLength = getBitLength(n);
        if(start < 0 || end <= 0 || bitLength == 0 || start > end || start == end || end > bitLength) {
            throw new IllegalArgumentException(String.format("Invalid bit range: [%d, %d] for input: %d", start, end, n));
        }
        var result = 0;
        //TODO do this with bit shifting/bit math
        for(int k = 0, i = start; i <= end; i++, k++) {
            result += (int)(getKthBit(n, i) == 1 ? Math.pow(2, k) : 0);
        }
        return result;
    }

    /**
     * Returns the Kth bit of n
     * @param n The integer input
     * @param k The bit index to retrieve
     * @return The Kth bit of n
     * @throws IllegalArgumentException if k is negative or greater than the number of bits in n
     */
    public static int getKthBit(int n, int k) {
        if(k > getBitLength(n)) {
            throw new IllegalArgumentException(String.format("Invalid bit index: %d requested for input: %d", k, n));
        }
        //Shift the relevant bit all the way to the right and compare it to a constant bit mask of 1
        return (n >> k) & 1;
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