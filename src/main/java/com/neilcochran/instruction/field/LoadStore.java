package com.neilcochran.instruction.field;

import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;
import lombok.Data;

@Data
public class LoadStore {
    private static final int L_BIT_INDEX = 20;
    private static final BitRange DATA_SIZE_RANGE = new BitRange(21, 22);
    private static final int U_BIT_INDEX = 23;

    private final int bits;
    private final int loadStoreBit;
    private final DataSize dataSize;
    private final int offsetAddSubBit;

    public LoadStore(int bits) {
        this.bits = bits;
        loadStoreBit = BitUtils.getKthBit(bits, L_BIT_INDEX);
        dataSize = switch (BitUtils.getBitRange(bits, DATA_SIZE_RANGE)) {
            case 0b00 -> DataSize.WORD;
            case 0b01 -> DataSize.HALF_WORD;
            case 0b11 -> DataSize.BYTE;
            default -> throw new IllegalArgumentException("Invalid LoadStore instruction DataSize bits");
        };
        offsetAddSubBit = BitUtils.getKthBit(bits, U_BIT_INDEX);
    }
}
