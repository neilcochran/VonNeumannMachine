package components;

import lombok.Data;

import java.util.Arrays;

@Data
public class RAM {
    private int[] memory;
    private int totalBytes;

    /**
     * Initialize the indicated bytes of RAM for a given word size
     * @param totalBytes The total bytes of RAM to be allocated
     * @param wordSize The word size in bits
     */
    public RAM(int totalBytes, int wordSize) {
        if(totalBytes % wordSize != 0) {
           throw new IllegalArgumentException("Invalid RAM bytes size: " + totalBytes + " for the given word size: " + wordSize);
        }
        this.memory = new int[totalBytes / wordSize];
        Arrays.fill(memory, 0);
    }

    public int getAddressData(int address) {
        return memory[address];
    }

    public void setAddressData(int address, int data) {
        memory[address] = data;
    }
}
