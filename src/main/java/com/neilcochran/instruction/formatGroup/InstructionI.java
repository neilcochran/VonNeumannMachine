package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.OpCodeInstructions;
import com.neilcochran.instruction.fields.InstructionFormat;
import com.neilcochran.instruction.fields.RotateConstant;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an I-Instruction
 */
@Getter
@Setter
public class InstructionI extends OpCodeInstructions {
    private static final int STATE_FLAG_INDEX =  20;
    private static final BitRange RN_OPERAND_RANGE = new BitRange(16, 19);
    private static final BitRange RD_OPERAND_RANGE = new BitRange(12, 15);
    private static final BitRange IMMEDIATE_ROTATE_RANGE = new BitRange(8, 11);
    private static final BitRange IMMEDIATE_CONSTANT_RANGE = new BitRange(0, 7);

    private final int stateFlagBit;
    private final int RN;
    private final int RD;
    private final RotateConstant rotateConstant;

    /**
     * Takes in an I-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid I-Instruction
     */
    public InstructionI(long instruction) {
        super(instruction, InstructionFormat.I);
        this.stateFlagBit = BitUtils.getKthBit(instruction, STATE_FLAG_INDEX);
        this.RN = BitUtils.getBitRange(instruction, RN_OPERAND_RANGE);
        this.RD = BitUtils.getBitRange(instruction, RD_OPERAND_RANGE);
        this.rotateConstant = new RotateConstant(BitUtils.getBitRange(instruction, IMMEDIATE_ROTATE_RANGE), BitUtils.getBitRange(instruction, IMMEDIATE_CONSTANT_RANGE));
    }

    @Override
    public String toString() {
        return String.format("InstructionI(instruction=%s condition=%s OpCode=%s, RN=%d RD=%d rotateConstant=%s)", getBinaryString(), condition, opCode.getName(), RN, RD, rotateConstant);

    }
}
