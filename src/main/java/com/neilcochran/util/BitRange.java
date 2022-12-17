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

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
