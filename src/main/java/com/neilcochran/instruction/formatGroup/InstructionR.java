package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.OpCodeInstructions;
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
public class InstructionR extends OpCodeInstructions {
    private static final int STATE_FLAG_INDEX =  20;
    private static final BitRange RN_OPERAND_RANGE = new BitRange(16, 19);
    private static final BitRange RD_OPERAND_RANGE = new BitRange(12, 15);
    private static final BitRange SHIFT_RANGE = new BitRange(5, 11);
    private static final BitRange RM_OPERAND_RANGE = new BitRange(0, 3);

    private final int stateFlagBit;
    private final int RN;
    private final int RD;
    private final Shift shift;
    private final int RM;

    /**
     * Takes in an R-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid R-Instruction
     */
    public InstructionR(long instruction) {
        super(instruction,  InstructionFormat.R);
        this.stateFlagBit = BitUtils.getKthBit(instruction, STATE_FLAG_INDEX);
        this.RN = BitUtils.getBitRange(instruction, RN_OPERAND_RANGE);
        this.RD = BitUtils.getBitRange(instruction, RD_OPERAND_RANGE);
        this.shift = new Shift(BitUtils.getBitRange(instruction, SHIFT_RANGE));
        this.RM = BitUtils.getBitRange(instruction, RM_OPERAND_RANGE);
    }

    @Override
    public String toString() {
        return String.format("InstructionR(instruction=%s condition=%s OpCode=%s, RN=%d RD=%d RM=%d Shift=%s)", getBinaryString(), condition, opCode.getName(), RN, RD, RM, shift);
    }
}
