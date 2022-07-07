package com.neilcochran.instruction.formatGroup.D_X;

import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.Shift;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an X-Instruction
 */
@Getter
@Setter
public class InstructionX extends LoadStoreInstruction {
    private static final BitRange SHIFT_RANGE = new BitRange(5, 11);
    private static final BitRange RM_RANGE = new BitRange(0, 3);

    private final Shift shift;
    private final int RM;

    /**
     * Takes in an X-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid X-Instruction
     */
    public InstructionX(int instruction) {
        super(instruction, InstructionFormat.X);
        shift = new Shift(BitUtils.getBitRange(instruction, SHIFT_RANGE));
        RM = BitUtils.getBitRange(instruction, RM_RANGE);
    }

    @Override
    public String toString() {
        return String.format("InstructionD(instruction=%s condition=%s U=%d L=%d RN=%d RD=%d shift=%s RM=%d)", getBinaryString(), condition, getLoadStoreBit(), getOffsetAddSubBit(), RN, RD, shift, RM);
    }
}
