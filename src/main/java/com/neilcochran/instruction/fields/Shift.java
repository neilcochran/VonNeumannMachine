package com.neilcochran.instruction.fields;

import com.neilcochran.util.BitUtils;
import lombok.Data;


@Data
public class Shift {
    private static final int SHIFT_TYPE_BIT_LENGTH = 4;
    private final ShiftType shiftType;
    private final int shiftAmountBits; //0-31


    public Shift(int shiftAmountBits) {
        if(BitUtils.getBitLength(shiftAmountBits) > SHIFT_TYPE_BIT_LENGTH) {
           throw new IllegalArgumentException(String.format("Invalid shift type bits: %s exceeded the maximum bit length: %d", Integer.toBinaryString(shiftAmountBits), SHIFT_TYPE_BIT_LENGTH));
        }
        this.shiftType = ShiftType.fromBits(shiftAmountBits);
        this.shiftAmountBits = shiftAmountBits;
    }

}
