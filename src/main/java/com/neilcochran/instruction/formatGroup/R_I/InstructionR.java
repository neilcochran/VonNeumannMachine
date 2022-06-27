package com.neilcochran.instruction.formatGroup.R_I;

import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.Shift;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an R-Instruction
 */
@Getter
@Setter
public class InstructionR extends OpCodeInstruction {
    private static final BitRange SHIFT_RANGE = new BitRange(5, 11);
    private static final BitRange RM_OPERAND_RANGE = new BitRange(0, 3);

    private final Shift shift;
    private final int RM;

    /**
     * Takes in an R-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid R-Instruction
     */
    public InstructionR(int instruction) {
        super(instruction,  InstructionFormat.R);
        this.shift = new Shift(BitUtils.getBitRange(instruction, SHIFT_RANGE));
        this.RM = BitUtils.getBitRange(instruction, RM_OPERAND_RANGE);
    }

    @Override
    public String toString() {
        return String.format("InstructionR(instruction=%s condition=%s OpCode=%s, RN=%d RD=%d RM=%d Shift=%s)", getBinaryString(), condition, opCode, RN, RD, RM, shift);
    }
}
