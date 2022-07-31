package com.neilcochran.component;

import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;

import java.util.Arrays;

/**
 * Represents byte addressable memory
 */
public class Memory {
    private final int[] memory;

    /**
     * The total number of addressable bytes of memory
     */
    public final int totalBytes;

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
        var address = new Address(byteAddress, dataSize);
        return BitUtils.getBitRange(memory[address.getMemoryIndex()], address.getBitStartIndex(), address.getBitEndIndex());
    }

    /**
     * Store data in memory at byteAddress in the indicated dataSize
     * @param byteAddress The target byte address in memory
     * @param dataSize The size of the data to be stored
     * @param data The data to be stored
     */
    public void storeData(int byteAddress, DataSize dataSize, int data) {
        var address = new Address(byteAddress, dataSize);
        var targetMem = memory[address.getMemoryIndex()];
        for(var i = address.getBitStartIndex(); i < address.getBitEndIndex(); i++) {
            //get the target bit
            var bit = BitUtils.getKthBit(data,i - address.getBitStartIndex());
            //set the target bit in target memory
            targetMem = BitUtils.bitToBool(bit) ? BitUtils.setKthBit(targetMem, i) : BitUtils.clearKthBit(targetMem, i);
        }
        //update memory
        memory[address.getMemoryIndex()] = targetMem;
        //        //TODO use bit masking logic to clear/set bits instead of looping. Broken WIP below
        //        //get the target bit range
        //        var targetBits = BitUtils.getBitRange(targetMem, start, end);
        //        //var targetBits = BitUtils.getBitRange(targetMem, 0, dataSize.getBitLength() - 1);
        //        //create a 1s complement bit mask of the target bits
        //        var clearMask = ~targetBits;
        //        //AND the target memory with the bit mask to clear the relevant bits
        //        targetMem &= clearMask;
        //        //OR the target memory with the data to be stored
        //        targetMem |= data;
        //        //update memory
        //        memory[memIndex] = targetMem;

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

    /**
     * Represents an Address in memory.
     * Memory is an array of 32-bit integers (words) that supports byte addressing. Thus, additional calculated address information is needed.
     */
    private class Address {
        private final int byteAddress;
        private final int memoryIndex;
        private final int bitOffset;
        private final int bitStartIndex;
        private final int bitEndIndex;

        /**
         * Constructs an Address for the given byte address and DataSize
         * @param byteAddress The target byte address
         * @param dataSize The target DataSize
         */
        public Address(int byteAddress, DataSize dataSize) {
            if(!isValidByteAddress(byteAddress)) {
                throw new IllegalArgumentException("Invalid byte address: " + byteAddress);
            }
            this.byteAddress = byteAddress;
            this.memoryIndex = calculateMemoryIndex(this.byteAddress);
            this.bitOffset = this.memoryIndex * DataSize.WORD.getBitLength();
            this.bitStartIndex = (this.byteAddress * DataSize.BYTE.getBitLength()) - bitOffset;
            this.bitEndIndex = bitStartIndex + dataSize.getBitLength() - 1;
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
         * Get the byte address
         * @return The byte address
         */
        public int getByteAddress() {
            return byteAddress;
        }

        /**
         * Get the relevant index in the memory array
         * @return The relevant index in the memory array
         */
        public int getMemoryIndex() {
            return memoryIndex;
        }

        /**
         * Get the bit offset
         * @return The bit offset
         */
        public int getBitOffset() {
            return bitOffset;
        }

        /**
         * Get the starting bit index
         * @return The starting bit index
         */
        public int getBitStartIndex() {
            return bitStartIndex;
        }

        /**
         * Get the end bit index
         * @return The end bit index
         */
        public int getBitEndIndex() {
            return bitEndIndex;
        }
    }
}
