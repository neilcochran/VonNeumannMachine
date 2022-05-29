package com.neilcochran.instruction.field;

import com.neilcochran.util.BitUtils;
import lombok.Data;

@Data
public class LoadStore {
    private static final int L_BIT_INDEX = 20;
    private static final int U_BIT_INDEX = 23;

    private final int bits;
    private final int loadStoreBit;
    private final int offsetAddSubBit;

    public LoadStore(int bits) {
        this.bits = bits;
        this.loadStoreBit = BitUtils.getKthBit(bits, L_BIT_INDEX);
        this.offsetAddSubBit = BitUtils.getKthBit(bits, U_BIT_INDEX);
    }
}
