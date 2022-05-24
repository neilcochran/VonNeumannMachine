package com.neilcochran.util;

/**
 * Define the various data sizes that will be worked with
 */
public enum DataSize {
    WORD(32),
    HALF_WORD(16),
    BYTE(8);

    private final int bitLength;

    /**
     * Construct a DataSize enum for a given bit length
     * @param bitLength The bit length of the DataSize
     */
    DataSize(final int bitLength) {
        this.bitLength = bitLength;
    }

    /**
     * Get the bit length of the DataSize
     * @return The bit length of the DataSize
     */
    public int getBitLength() {
        return bitLength;
    }
}
