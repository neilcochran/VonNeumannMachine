package com.neilcochran.instruction.fields;

import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Data;

@Data
public class LoadStore {
    private static final BitRange L_BIT_RANGE = new BitRange(0,0); //TODO RANGE
    private static final BitRange U_BIT_RANGE = new BitRange(0,0); //TODO RANGE

    private final int bits;
    private final int loadStoreBit;
    private final int offsetAddSubBit;

    public LoadStore(int bits) {
        this.bits = bits;
        this.loadStoreBit = BitUtils.getBitRange(bits, L_BIT_RANGE);
        this.offsetAddSubBit = BitUtils.getBitRange(bits, U_BIT_RANGE);
    }
}
