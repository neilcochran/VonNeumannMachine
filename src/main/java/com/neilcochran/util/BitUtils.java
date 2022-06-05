package com.neilcochran.util;

import javax.xml.crypto.Data;

/**
 * A collection of static helper utilities
 */
public class BitUtils {

    /**
     * Calculates the number of bits needed to represent an integer.
     * Calculate log2(n) indirectly using log rules or return 1 if the input is 0
     * @param n The input integer
     * @return The number of bits needed to represent the input integer n
     */
    public static int getBitLength(long n) {
        return n == 0 ? 1 : (int)((Math.log(n) / Math.log(2))) + 1;
    }

    /**
     * Check if a given int exceeds the given maxBitLength when interpreting it in binary
     * @param toValidate The int whose bit length is to be validated
     * @param maxBitLength The maximum allowed number of bits
     * @return True if the int uses maxBitLength or fewer bits, false if it requires more bits than maxBitLength
     */
    public static boolean validateBitLength(long toValidate, int maxBitLength) {
        return getBitLength(toValidate) <= maxBitLength;
    }

    /**
     * Returns the Kth bit of n
     * @param n The integer input
     * @param k The bit index to retrieve
     * @return The Kth bit of n
     */
    public static int getKthBit(long n, int k) {
        //Shift the relevant bit all the way to the right and compare it to a constant bit mask of 1
        return (int)(n >> k) & 1;
    }

    /**
     * Check if a bit range is valid by itself
     * @param start The index of the starting bit of the range
     * @param end The index of the ending bit of the range
     * @return True if the bit range is valid, false otherwise
     */
    public static boolean isValidBitRange(int start, int end) {
        return start >= 0 || end >= 0 || start < end;
    }

    /**
     * For a given integer n return the int value of just the bits of n between [start, end]
     * @param n The integer to get the bit range value from
     * @param start The index of the bit at the start of the range (inclusive)
     * @param end The index of the bit at the end of the range (inclusive)
     * @return The integer value of just the bits of n between [start, end]
     * @throws IllegalArgumentException if the bit range is invalid for the given input
     */
    public static int getBitRange(long n, int start, int end) {
        if(!isValidBitRange(start, end)) {
            throw new IllegalArgumentException(String.format("Invalid bit range: [%d, %d]", start, end));
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
     * For a given integer n return the int value of just the bits of n between the BitRange's [startIndex, endIndex]
     * @param n The integer to get the bit range value from
     * @param bitRange The BitRange which holds the range indices to be retrieved
     * @return The integer value of just the bits of n between the BitRange's [startIndex, endIndex]
     * @throws IllegalArgumentException if the bit range is invalid for the given input
     */
    public static int getBitRange(long n, BitRange bitRange) {
        return getBitRange(n, bitRange.getStartIndex(), bitRange.getEndIndex());
    }

    //TODO replace Integer/Long#toBinaryString usage since this handles both & supports padding
    public static String getBinaryString(long data, DataSize dataSize, boolean leftPad) {
        if(dataSize == DataSize.WORD) {
            return Long.toBinaryString(data).substring(32);
        } else {
            var binaryString = Integer.toBinaryString((int) data);
            if(leftPad) {
                StringBuilder stringBuilder = new StringBuilder(dataSize.getBitLength());
                stringBuilder.append("0".repeat(dataSize.getBitLength() - BitUtils.getBitLength(data)));
                binaryString = stringBuilder.append(binaryString).toString();
            }
            return binaryString;
        }
    }
}