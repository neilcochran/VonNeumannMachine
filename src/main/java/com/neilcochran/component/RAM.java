package com.neilcochran.component;

import com.neilcochran.util.DataSize;
import lombok.Data;

import java.util.Arrays;

/**
 * Represents Random Access Memory (RAM)
 */
@Data
public class RAM {
    private long[] memory;
    private int totalBytes;

    /**
     * Represents RAM. Initialize the indicated bytes of RAM for a given word size
     * @param totalBytes The total bytes of RAM to be allocated
     * @throws IllegalArgumentException if the totalBytes is not a valid number for the word size
     * @see VonNeumannMachine#WORD_SIZE
     */
    public RAM(int totalBytes) {
        if(totalBytes % VonNeumannMachine.WORD_SIZE != 0) {
           throw new IllegalArgumentException("Invalid RAM bytes size: " + totalBytes + " for the given word size: " + VonNeumannMachine.WORD_SIZE);
        }
        this.totalBytes = totalBytes;
        this.memory = new long[totalBytes / VonNeumannMachine.WORD_SIZE];
        Arrays.fill(memory, 0);
    }

    public long loadData(int byteAddress, DataSize dataSize) {
        if(!isValidByteAddress(byteAddress)) {
            throw new IllegalArgumentException(String.format("Invalid byte address: %d", byteAddress));
        }
        return switch (dataSize) {
            case BYTE ->  loadByte(byteAddress);
            case HALF_WORD -> loadHalfWord(byteAddress);
            case WORD -> loadWord(byteAddress);
        };
    }

    public void storeData(int byteAddress, DataSize dataSize, long data) {
        if(!isValidByteAddress(byteAddress)) {
            throw new IllegalArgumentException(String.format("Invalid byte address: %d", byteAddress));
        }
        switch (dataSize) {
            case BYTE -> storeByte(byteAddress, (int) data);
            case HALF_WORD -> storeHalfWord(byteAddress, (int) data);
            case WORD -> storeWord(byteAddress, data);
        }
    }

    private void storeByte(int byteAddress, int data) {}

    private void storeHalfWord(int byteAddress, int data) {}

    private void storeWord(int byteAddress, long data) {}


    private int loadByte(int byteAddress) {
        //TODO implement
        return -1;
    }

    private int loadHalfWord(int byteAddress) {
        //TODO implement
        return -1;
    }

    private long loadWord(int byteAddress) {
        var memoryIndex = (byteAddress * 4 ) / DataSize.WORD.getBitLength();
        return memory[memoryIndex];
    }

    private boolean isValidByteAddress(int byteAddress) {
        return byteAddress >= 0 && byteAddress < totalBytes;
    }

    public void loadProgramData(long[] programData) {
        if(programData.length > memory.length) {
            throw new IllegalArgumentException("Cannot load a program that is bigger than memory");
        }
        System.arraycopy(programData, 0, memory, 0, programData.length);
    }
}
