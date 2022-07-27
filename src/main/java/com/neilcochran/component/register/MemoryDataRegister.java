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

    //load the register with the data located at the address currently held in the memory address register
    public void loadData(DataSize dataSize, boolean signExtend) {
        int loadedValue = memory.loadData(memoryAddressRegister.getData(), dataSize);
        //if not loading a full word, check if we are sign extending or zero padding
        if(!DataSize.WORD.equals(dataSize)) {
            if(signExtend) {
                this.data = signExtend(loadedValue, dataSize);
            } else {
                this.data = zeroPad(loadedValue, dataSize);
            }
        } else {
            this.data = loadedValue;
        }
    }

    //store this register's data in memory at the address held in the MAR
    public void storeData(DataSize dataSize) {
        memory.storeData(memoryAddressRegister.getData(), dataSize, getData());
    }

    private static int signExtend(int value, DataSize dataSize) {
        //TODO impl
        return value;
    }

    private static int zeroPad(int value, DataSize dataSize) {
        //TODO impl
        return value;
    }
}
