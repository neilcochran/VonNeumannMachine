package com.neilcochran.util;

/**
 * A enum that defines the supported data sizes (bit lengths)
 */
public enum DataSize {
    //TODO add DOUBLE(64) support
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
