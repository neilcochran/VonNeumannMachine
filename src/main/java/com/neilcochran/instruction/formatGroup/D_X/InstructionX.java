package com.neilcochran.instruction.formatGroup.D_X;

import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.Shift;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Instruction of the X format group
 */
@Getter
@Setter
public class InstructionX extends LoadStoreInstruction {

    /**
     * The bit range that encodes the Shift
     * @see com.neilcochran.instruction.formatGroup.D_X.InstructionX#shift
     */
    private static final BitRange SHIFT_RANGE = new BitRange(5, 11);

    /**
     * The bit range that denotes RM
     * @see com.neilcochran.instruction.formatGroup.D_X.InstructionX#RM
     */
    private static final BitRange RM_RANGE = new BitRange(0, 3);

    private final Shift shift;
    private final int RM;

    /**
     * Constructs an InstructionX from an integer that encodes for an instruction of the X format group
     * @param instruction The integer that encodes for the given InstructionX
     * @throws IllegalArgumentException if the instruction is not a valid InstructionX
     */
    public InstructionX(int instruction) {
        super(instruction, InstructionFormat.X);
        shift = new Shift(BitUtils.getBitRange(instruction, SHIFT_RANGE));
        RM = BitUtils.getBitRange(instruction, RM_RANGE);
    }

    /**
     * Get a string representation of the InstructionX instance
     * @return A string representation of the InstructionX instance
     */
    @Override
    public String toString() {
        return String.format("InstructionD(instruction=%s condition=%s U=%d L=%d RN=%d RD=%d shift=%s RM=%d)", getBinaryString(), condition, getLoadStoreBit(), getOffsetAddSubBit(), RN, RD, shift, RM);
    }
}
