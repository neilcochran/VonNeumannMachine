package com.neilcochran.instruction.field;

import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Data;


@Data
public class Shift {
    private static final BitRange SHIFT_TYPE_RANGE = new BitRange(0, 1);
    private static final BitRange SHIFT_AMOUNT_RANGE = new BitRange(2, 6);

    private final ShiftType shiftType;
    private final int shiftAmountBits; //0-31


    public Shift(int shiftBits) {
        this.shiftType = ShiftType.fromBits(BitUtils.getBitRange(shiftBits, SHIFT_TYPE_RANGE));
        this.shiftAmountBits = BitUtils.getBitRange(shiftBits, SHIFT_AMOUNT_RANGE);
    }

}
