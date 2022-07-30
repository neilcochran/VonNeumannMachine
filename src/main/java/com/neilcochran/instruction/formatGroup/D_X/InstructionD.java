package com.neilcochran.instruction.formatGroup.D_X;

import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.RotateConstant;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Instruction of the D format group
 */
@Getter
@Setter
public class InstructionD extends LoadStoreInstruction {

    /**
     * The bit range that encodes the rotate portion of the `RotateConstant`
     * @see com.neilcochran.instruction.formatGroup.D_X.InstructionD#rotateConstant
     */
    private static final BitRange IMMEDIATE_ROTATE_RANGE = new BitRange(8, 11);

    /**
     * The bit range that denotes the constant portion of the `RotateConstant`
     * @see com.neilcochran.instruction.formatGroup.D_X.InstructionD#rotateConstant
     */
    private static final BitRange IMMEDIATE_CONSTANT_RANGE = new BitRange(0, 7);

    private final RotateConstant rotateConstant;

    /**
     * Constructs an InstructionD from an integer that encodes for an instruction of the D format group
     * @param instruction The integer that encodes for the given `InstructionD`
     * @throws IllegalArgumentException if the instruction is not a valid `InstructionD`
     */
    public InstructionD(int instruction) {
        super(instruction, InstructionFormat.D);
        rotateConstant = new RotateConstant(BitUtils.getBitRange(instruction, IMMEDIATE_ROTATE_RANGE), BitUtils.getBitRange(instruction, IMMEDIATE_CONSTANT_RANGE));
    }

    /**
     * Get a string representation of the `InstructionD` instance
     * @return A string representation of the `InstructionD` instance
     */
    @Override
    public String toString() {
        return String.format("InstructionD(instruction=%s condition=%s U=%d L=%d RN=%d RD=%d rotateConstant=%s)", getBinaryString(), condition, getLoadStoreBit(), getOffsetAddSubBit(), RN, RD, rotateConstant);
    }
}
