package com.neilcochran.component;

import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;
import lombok.Data;

import java.util.Arrays;

/**
 * Represents Memory
 */
@Data
public class Memory {
    private final int[] memory;
    private final int totalBytes;

    /**
     * Represents memory. Initialize the indicated bytes of memory for a given word size
     * @param totalBytes The total bytes of memory to be allocated
     * @throws IllegalArgumentException if the totalBytes is not a valid number for the word size
     * @see VonNeumannMachine#WORD_SIZE
     */
    public Memory(int totalBytes) {
        if(totalBytes % VonNeumannMachine.WORD_SIZE != 0) {
           throw new IllegalArgumentException("Invalid memory bytes size: " + totalBytes + " for the given word size: " + VonNeumannMachine.WORD_SIZE);
        }
        this.totalBytes = totalBytes;
        this.memory = new int[totalBytes / VonNeumannMachine.WORD_SIZE];
        Arrays.fill(memory, 0);
    }

    public int loadData(int byteAddress, DataSize dataSize) {
        if(!isValidByteAddress(byteAddress)) {
            throw new IllegalArgumentException(String.format("Invalid byte address: %d", byteAddress));
        }
        return BitUtils.getBitRange(memory[calculateMemoryIndex(byteAddress)], 0, dataSize.getBitLength() - 1);
    }

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

    private int calculateMemoryIndex(int byteAddress) {
        return (byteAddress * 8 ) / DataSize.WORD.getBitLength();
    }

    private boolean isValidByteAddress(int byteAddress) {
        return byteAddress >= 0 && byteAddress < totalBytes;
    }

    public void loadProgramData(int[] programData) {
        if(programData.length > memory.length) {
            throw new IllegalArgumentException("Cannot load a program that is bigger than memory");
        }
        System.arraycopy(programData, 0, memory, 0, programData.length);
    }
}
