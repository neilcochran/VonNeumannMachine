package com.neilcochran.instruction.formatGroup.R_I;

import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.RotateConstant;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an I-Instruction
 */
@Getter
@Setter
public class InstructionI extends OpCodeInstruction {
    private static final BitRange IMMEDIATE_ROTATE_RANGE = new BitRange(8, 11);
    private static final BitRange IMMEDIATE_CONSTANT_RANGE = new BitRange(0, 7);

    private final RotateConstant rotateConstant;

    /**
     * Takes in an I-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid I-Instruction
     */
    public InstructionI(int instruction) {
        super(instruction, InstructionFormat.I);
        this.rotateConstant = new RotateConstant(BitUtils.getBitRange(instruction, IMMEDIATE_ROTATE_RANGE), BitUtils.getBitRange(instruction, IMMEDIATE_CONSTANT_RANGE));
    }

    @Override
    public String toString() {
        return String.format("InstructionI(instruction=%s condition=%s OpCode=%s, RN=%d RD=%d rotateConstant=%s)", getBinaryString(), condition, opCode, RN, RD, rotateConstant);
    }
}
