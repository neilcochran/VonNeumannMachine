package com.neilcochran.component.register;

import com.neilcochran.component.Memory;
import com.neilcochran.util.DataSize;

public class MemoryDataRegister extends Register {

    private final Register memoryAddressRegister;
    private final Memory memory;

    public MemoryDataRegister(Register memoryAddressRegister, Memory memory) {
        super("MDR", "MDR");
        this.memoryAddressRegister = memoryAddressRegister;
        this.memory = memory;
    }

    //populate the register with the data located at the address currently held in the memory address register
    public void loadData(DataSize dataSize) {
        this.data = memory.loadData(memoryAddressRegister.getData(), dataSize);
    }
}
