package com.neilcochran.component.register;

import com.neilcochran.component.Memory;
import com.neilcochran.util.DataSize;

/**
 * Represents a special internal register used to store data being fetched or sent to memory.
 * This is the only avenue to and from memory
 */
public class MemoryDataRegister extends Register {

    private final Register memoryAddressRegister;
    private final Memory memory;

    /**
     * Constructs a new MemoryDataRegister (MDR). The MDR uses the Memory Address Register (MAR) to determine which
     * memory location (byte address) to send or fetch data from.
     * @param memoryAddressRegister A reference to the register used as the Memory Address Register (MAR)
     * @param memory A reference to the machines memory
     */
    public MemoryDataRegister(Register memoryAddressRegister, Memory memory) {
        super("MDR", "MDR");
        this.memoryAddressRegister = memoryAddressRegister;
        this.memory = memory;
    }

    /**
     * Loads the register with the data located at the address currently held in the memory address register (MAR)
     * @param dataSize The `DataSize` being loaded from memory
     * @param signExtend A flag indicating if the value should be sign extended (`true`) or zero padded (`false`)
     */
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

    /**
     * Store the register's data in memory at the address held in the Memory Address Register (MAR)
     * @param dataSize The `DataSize` of the value to be stored
     */
    public void storeData(DataSize dataSize) {
        memory.storeData(memoryAddressRegister.getData(), dataSize, getData());
    }

    /**
     * Sign extend an integer `value` to fit the given `dataSize`
     * @param value The integer to be sign extended
     * @param dataSize The target `DataSize`
     * @return The resulting sign extended integer
     */
    private static int signExtend(int value, DataSize dataSize) {
        //TODO impl
        return value;
    }

    /**
     * Zero pad an integer `value` to fit the given `dataSize`
     * @param value The integer to be zero padded
     * @param dataSize The target `DataSize`
     * @return The resulting zero padded integer
     */
    private static int zeroPad(int value, DataSize dataSize) {
        //TODO REMOVE - zero padding won't be needed since the higher bits will be blank as is (unless placing in a 'dirty' field)
        //but if the target register is always zeroed out then zero padding is never needed
        return value;
    }
}
