package com.neilcochran.components.registers;

import com.neilcochran.components.RAM;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Memory Data Register
 */
@Getter
@Setter //Note: We normally would use @Data here but that requires Register to have a default constructor (not wanted in this case)
public class MemoryDataRegister extends Register {
    private final RAM ram;
    private final MemoryAddressRegister memoryAddressRegister;

    /**
     * Creates a new Memory Data Register (MDR)
     * @param memoryAddressRegister A reference to the MemoryAddressRegister which is used to determine which memory location to read/write to
     * @param ram A reference to the RAM which will be read from and/or written to
     */
    public MemoryDataRegister(MemoryAddressRegister memoryAddressRegister, RAM ram) {
        super("MDR");
        this.ram = ram;
        this.memoryAddressRegister = memoryAddressRegister;
    }

    /**
     * Sets and returns the data from RAM using the address currently in the Memory Address Register
     * @return The data located at the address currently in the Memory Address Register
     */
    public int loadDataFromMemory() {
        this.data = ram.getAddressData(this.memoryAddressRegister.getData());
        return this.data;
    }
}
