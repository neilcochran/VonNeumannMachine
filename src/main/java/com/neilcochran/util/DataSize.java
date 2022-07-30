package com.neilcochran.util;

/**
 * A enum that defines the supported data sizes (bit lengths)
 */
public enum DataSize {
    //TODO add DOUBLE(64) support

    /**
     * A 32 bit word
     */
    WORD(32),

    /**
     * A 16 bit half word
     */
    HALF_WORD(16),

    /**
     * A byte (8 bits)
     */
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
