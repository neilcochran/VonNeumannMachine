package com.neilcochran.util;

/**
 * A wrapper representing a valid bit range
 */
public class BitRange {
    private int startIndex;
    private int endIndex;

    /**
     * Constructs a new BitRange with a start and end index
     * @param startIndex The start index of the range
     * @param endIndex The end index of the range
     * @throws IllegalArgumentException If the range is invalid
     */
    public BitRange(int startIndex, int endIndex) {
       if(!BitUtils.isValidBitRange(startIndex, endIndex)) {
            throw new IllegalArgumentException(String.format("Invalid bit range: [%d, %d]", startIndex, endIndex));
        }
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * Get the start index
     * @return The start index
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Set the start index
     * @param startIndex The start index
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * Get the end index
     * @return The end index
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Set the end index
     * @param endIndex The end index
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
