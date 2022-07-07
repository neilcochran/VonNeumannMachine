package com.neilcochran.instruction.formatGroup.D_X;

import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.RotateConstant;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a D-Instruction
 */
@Getter
@Setter
public class InstructionD extends LoadStoreInstruction {
    private static final BitRange IMMEDIATE_ROTATE_RANGE = new BitRange(8, 11);
    private static final BitRange IMMEDIATE_CONSTANT_RANGE = new BitRange(0, 7);
    private final RotateConstant rotateConstant;

    /**
     * Takes in an D-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid D-Instruction
     */
    public InstructionD(int instruction) {
        super(instruction, InstructionFormat.D);
        rotateConstant = new RotateConstant(BitUtils.getBitRange(instruction, IMMEDIATE_ROTATE_RANGE), BitUtils.getBitRange(instruction, IMMEDIATE_CONSTANT_RANGE));
    }

    @Override
    public String toString() {
        return String.format("InstructionD(instruction=%s condition=%s U=%d L=%d RN=%d RD=%d rotateConstant=%d)", getBinaryString(), condition, getLoadStoreBit(), getOffsetAddSubBit(), RN, RD, rotateConstant);
    }
}
