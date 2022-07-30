package com.neilcochran.instruction.field;

import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Data;

/**
 * Represents a bit shift encoded in two parts: a shift type (bits [0,1]) and a shift amount (bits [2, 6])
 */
@Data
public class Shift {

    /**
     * The bit range that encodes what type of shift is to be performed
     * @see com.neilcochran.instruction.field.ShiftType
     */
    private static final BitRange SHIFT_TYPE_RANGE = new BitRange(0, 1);

    /**
     * The bit range that designates the number of bits to shift by (0-31 bits)
     * @see com.neilcochran.instruction.field.Shift#shiftAmountBits
     */
    private static final BitRange SHIFT_AMOUNT_RANGE = new BitRange(2, 6);

    private final ShiftType shiftType;
    private final int shiftAmountBits;

    /**
     * Constructs a new Shift from the given `shiftBits`
     * @param shiftBits The integer that encodes for the given Shift
     */
    public Shift(int shiftBits) {
        this.shiftType = ShiftType.fromBits(BitUtils.getBitRange(shiftBits, SHIFT_TYPE_RANGE));
        this.shiftAmountBits = BitUtils.getBitRange(shiftBits, SHIFT_AMOUNT_RANGE);
    }

}
