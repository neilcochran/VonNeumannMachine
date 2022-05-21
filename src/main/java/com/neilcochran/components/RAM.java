package com.neilcochran.components;

import lombok.Data;

import java.util.Arrays;

/**
 * Represents Random Access Memory (RAM)
 */
@Data
public class RAM {
    private int[] memory;
    private int totalBytes;

    /**
     * Represents RAM. Initialize the indicated bytes of RAM for a given word size
     * @param totalBytes The total bytes of RAM to be allocated
     * @param wordSize The word size in bits
     * @throws IllegalArgumentException if the totalBytes is not a valid number for the given wordSize
     */
    public RAM(int totalBytes, int wordSize) {
        if(totalBytes % wordSize != 0) {
           throw new IllegalArgumentException("Invalid RAM bytes size: " + totalBytes + " for the given word size: " + wordSize);
        }
        this.memory = new int[totalBytes / wordSize];
        Arrays.fill(memory, 0);
    }

    /**
     * Get the data located at the indicated address
     * @param address The target address in RAM
     * @return The data located at the given address
     */
    public int getAddressData(int address) {
        return memory[address];
    }

    /**
     * Sets the given data at the indication address (location in RAM)
     * @param address The target address in RAM
     * @param data The data to set at the target address
     */
    public void setAddressData(int address, int data) {
        memory[address] = data;
    }
}
