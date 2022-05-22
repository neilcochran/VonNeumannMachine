package com.neilcochran.util;

/**
 * Define the various data sizes that will be worked with
 */
public enum DataSize {
    WORD(32),
    HALF_WORD(16),
    BYTE(8);

    private final int bitLength;

    DataSize(final int bitLength) {
        this.bitLength = bitLength;
    }

    public int getBitLength() {
        return bitLength;
    }
}
