package com.neilcochran.component;

import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;
import lombok.Data;

import java.util.Arrays;

/**
 * Represents byte addressable memory
 */
@Data
public class Memory {
    private final int[] memory;
    private final int totalBytes;

    /**
     * Represents byte addressable memory. Initialize the indicated bytes of memory for a given word size
     * @param totalBytes The total bytes of memory to be allocated
     * @throws IllegalArgumentException if the totalBytes is not a valid number for the word size
     */
    public Memory(int totalBytes) {
        if(totalBytes % VonNeumannMachine.WORD_SIZE != 0) {
           throw new IllegalArgumentException("Invalid memory bytes size: " + totalBytes + " for the given word size: " + VonNeumannMachine.WORD_SIZE);
        }
        this.totalBytes = totalBytes;
        this.memory = new int[totalBytes / VonNeumannMachine.WORD_SIZE];
        Arrays.fill(memory, 0);
    }

    /**
     * Fetch the data at byteAddress in memory for the given dataSize
     * @param byteAddress The byte address of the target data in memory
     * @param dataSize The size of the data to be fetched
     * @return The data at byteAddress in memory for the given dataSize
     */
    public int loadData(int byteAddress, DataSize dataSize) {
        if(!isValidByteAddress(byteAddress)) {
            throw new IllegalArgumentException(String.format("Invalid byte address: %d", byteAddress));
        }
        return BitUtils.getBitRange(memory[calculateMemoryIndex(byteAddress)], 0, dataSize.getBitLength() - 1);
    }

    /**
     * Store data in memory at byteAddress in the indicated dataSize
     * @param byteAddress The target byte address in memory
     * @param dataSize The size of the data to be stored
     * @param data The data to be stored
     */
    public void storeData(int byteAddress, DataSize dataSize, int data) {
        if(!isValidByteAddress(byteAddress)) {
            throw new IllegalArgumentException(String.format("Invalid byte address: %d", byteAddress));
        }
        var memIndex = calculateMemoryIndex(byteAddress);
        var targetMem = memory[memIndex];
        //get the target bit range
        var targetBits = BitUtils.getBitRange(targetMem, 0, dataSize.getBitLength() - 1);
        //create a 1s complement bit mask of the target bits
        var clearMask = ~targetBits;
        //AND the target memory with the bit mask to clear the relevant bits
        targetMem &= clearMask;
        //OR the target memory with the data to be stored
        targetMem |= data;
        //update memory
        memory[memIndex] = targetMem;
    }

    /**
     * For a given byteAddress calculate the corresponding memory array index
     * @param byteAddress The byte address to be converted into an array index
     * @return The memory array index for the given byteAddress
     */
    private int calculateMemoryIndex(int byteAddress) {
        return (byteAddress * 8 ) / DataSize.WORD.getBitLength();
    }

    /**
     * Validates a given byte address. A byte address is invalid if it is negative, or greater than the total number of bytes
     * @param byteAddress The byte address to validate
     * @return true if the byte address is valid, false otherwise
     */
    private boolean isValidByteAddress(int byteAddress) {
        return byteAddress >= 0 && byteAddress < totalBytes;
    }

    /**
     * Load an array of program data into memory
     * @param programData The array of program data to be loaded into memory
     */
    public void loadProgramData(int[] programData) {
        if(programData.length > memory.length) {
            throw new IllegalArgumentException("Cannot load a program that is bigger than memory");
        }
        System.arraycopy(programData, 0, memory, 0, programData.length);
    }
}
