package com.neilcochran.instruction.formatGroup.R_I;

import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.Shift;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Instruction of the R format group
 */
@Getter
@Setter
public class InstructionR extends OpCodeInstruction {

    /**
     * The bit range that encodes the Shift
     * @see com.neilcochran.instruction.formatGroup.R_I.InstructionR#shift
     */
    private static final BitRange SHIFT_RANGE = new BitRange(5, 11);

    /**
     * The bit range that denotes RM
     * @see com.neilcochran.instruction.formatGroup.R_I.InstructionR#RM
     */
    private static final BitRange RM_OPERAND_RANGE = new BitRange(0, 3);

    private final Shift shift;
    private final int RM;

    /**
     * Constructs an InstructionR from an integer that encodes for an instruction of the R format group
     * @param instruction The integer that encodes for the given InstructionR
     * @throws IllegalArgumentException if the instruction is not a valid InstructionR
     */
    public InstructionR(int instruction) {
        super(instruction,  InstructionFormat.R);
        this.shift = new Shift(BitUtils.getBitRange(instruction, SHIFT_RANGE));
        this.RM = BitUtils.getBitRange(instruction, RM_OPERAND_RANGE);
    }

    /**
     * Get a string representation of the InstructionR instance
     * @return A string representation of the InstructionR instance
     */
    @Override
    public String toString() {
        return String.format("InstructionR(instruction=%s condition=%s OpCode=%s, RN=%d RD=%d RM=%d Shift=%s)", getBinaryString(), condition, opCode, RN, RD, RM, shift);
    }
}
