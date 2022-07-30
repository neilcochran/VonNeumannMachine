package com.neilcochran.util;

/**
 * A collection of static utility methods to help with bit manipulation
 */
public class BitUtils {

    /**
     * Calculates the minimum number of bits needed to represent an integer.
     * To do this, calculate log2(n) (indirectly using log rules) or return 1 if the input is 0
     * @param n The integer whose bit length is to be determined
     * @return The minimum number of bits needed to represent the integer n
     */
    public static int getBitLength(int n) {
        return n == 0 ? 1 : (int)((Math.log(n) / Math.log(2))) + 1;
    }

    /**
     * Check if an integer exceeds the given maxBitLength when interpreting it in binary
     * @param toValidate The integer whose bit length is to be validated
     * @param maxBitLength The maximum allowed number of bits
     * @return true if the integer uses maxBitLength or fewer bits, false if it requires more bits than maxBitLength
     */
    public static boolean validateBitLength(int toValidate, int maxBitLength) {
        return getBitLength(toValidate) <= maxBitLength;
    }

    /**
     * Returns the Kth bit of n
     * @param n The integer input
     * @param k The target bit index of n to retrieve
     * @return An integer representing the Kth bit of n
     */
    public static int getKthBit(int n, int k) {
        //Shift the relevant bit all the way to the right and compare it to a constant bit mask of 1
        return (n >> k) & 1;
    }

    /**
     * Set the Kth bit of n to 0
     * @param n The integer to be manipulated
     * @param k The bit index of n to be set to 0
     * @return The resulting integer after the Kth bit has been set to 0
     */
    public static int clearKthBit(int n, int k) {
        return setKthBit(n, k, true);
    }

    /**
     * Set the Kth bit of n to 1
     * @param n The integer to be manipulated
     * @param k The bit index of n to be set to 1
     * @return The resulting integer after the Kth bit has been set to 1
     */
    public static int setKthBit(int n, int k) {
        return setKthBit(n, k, false);
    }

    /**
     * Set the Kth bit of n to 1 if clearBit is false or 0 if clearBit is true
     * @param n The integer to be manipulated
     * @param k The target bit index of n to be manipulated
     * @param clearBit A flag representing if the target bit should be set to 0 (true) or 1 (false)
     * @return The resulting integer after the Kth bit has been manipulated
     */
    private static int setKthBit(int n, int k, boolean clearBit) {
        if(clearBit) {
            return n & ~(1 << k);
        }
        return n | (1 << k);
    }

    /**
     * Given an integer constant and an amount to rotate (which will be multiplied by 2), calculate the resulting integer
     * @param rotate The distance of the desired rotation. Note: rotate will be multiplied by 2 internally to conform to ARMv7 spec
     * @param constant The integer constant to be manipulated
     * @return The resulting integer of the rotation calculation
     */
    public static int calculateRotate(int rotate, int constant) {
        return Integer.rotateRight(constant, 2 * rotate) | Integer.rotateLeft(constant, 32 - (2 * rotate));
    }
    /**
     * Check if a bit range is valid irregardless of a target integer.
     * A valid bit range is any range with a non-negative start and end index where the start is less than the end
     * @param start The index of the starting bit of the range
     * @param end The index of the ending bit of the range
     * @return true if the bit range is valid, false otherwise
     */
    public static boolean isValidBitRange(int start, int end) {
        return start >= 0 || end >= 0 || start < end;
    }

    /**
     * For the integer n, return the integer value of just the bits of n between [start, end]
     * @param n The integer to extract the bit range value from
     * @param start The index of the bit at the start of the range (inclusive)
     * @param end The index of the bit at the end of the range (inclusive)
     * @return The integer value of just the bits of n between [start, end]
     * @throws IllegalArgumentException if the bit range is invalid for the given input n
     */
    public static int getBitRange(int n, int start, int end) {
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
     * For the integer n, return the integer value of just the bits of n for the given bitRange
     * @param n The integer to extract the bit range value from
     * @param bitRange The BitRange which holds the range indices to be retrieved
     * @return The integer value of just the bits of n indicated by the bitRange
     * @throws IllegalArgumentException if the bit range is invalid for the given input n
     */
    public static int getBitRange(int n, BitRange bitRange) {
        return getBitRange(n, bitRange.getStartIndex(), bitRange.getEndIndex());
    }

    /**
     * Return a string representing the given input integer (data) in binary according to the dataSize param and leftPad flag
     * @param data The integer to be represented in binary
     * @param dataSize The desired DataSize of the output string
     * @param leftPad A flag indicating if the output binary string should be left padded with 0's if needed
     * @return A binary String representation of the input parameter data
     */
    public static String getBinaryString(int data, DataSize dataSize, boolean leftPad) {
        var binaryString = Integer.toBinaryString(data);
        if(binaryString.length() > dataSize.getBitLength()) {
            binaryString = binaryString.substring(binaryString.length() - dataSize.getBitLength());
        }
        else if(leftPad && dataSize.getBitLength() > binaryString.length()) {
            var padLen = dataSize.getBitLength() - binaryString.length();
            binaryString = "0".repeat(padLen) + binaryString;
        }
        return binaryString;
    }

    /**
     * Parse an integer representing a single bit into a boolean where 0 maps to false and 1 maps to true
     * @param bit the input integer representing a single bit
     * @return true if the input integer is 1 or false if the input integer is 0
     * @throws IllegalArgumentException if the input integer is greater than a single bit
     */
    public static boolean bitToBool(int bit) {
        return switch (bit) {
            case 0 -> false;
            case 1 -> true;
            default -> throw new IllegalArgumentException("Bit values can only be 0 or 1 but encountered: " + bit);
        };
    }
}