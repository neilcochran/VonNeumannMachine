package com.neilcochran.instruction.formatGroup.R_I;

import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.RotateConstant;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Instruction of the I format group
 */
@Getter
@Setter
public class InstructionI extends OpCodeInstruction {

    /**
     * The bit range that encodes the rotate portion of the RotateConstant
     * @see com.neilcochran.instruction.formatGroup.R_I.InstructionI#rotateConstant
     */
    private static final BitRange IMMEDIATE_ROTATE_RANGE = new BitRange(8, 11);

    /**
     * The bit range that encodes the constant portion of the RotateConstant
     * @see com.neilcochran.instruction.formatGroup.R_I.InstructionI#rotateConstant
     */
    private static final BitRange IMMEDIATE_CONSTANT_RANGE = new BitRange(0, 7);

    private final RotateConstant rotateConstant;

    /**
     * Constructs an InstructionI from an integer that encodes for an instruction of the I format group
     * @param instruction The integer that encodes for the given InstructionI
     * @throws IllegalArgumentException if the instruction is not a valid InstructionI
     */
    public InstructionI(int instruction) {
        super(instruction, InstructionFormat.I);
        this.rotateConstant = new RotateConstant(BitUtils.getBitRange(instruction, IMMEDIATE_ROTATE_RANGE), BitUtils.getBitRange(instruction, IMMEDIATE_CONSTANT_RANGE));
    }

    /**
     * Get a string representation of the InstructionI instance
     * @return A string representation of the InstructionI instance
     */
    @Override
    public String toString() {
        return String.format("InstructionI(instruction=%s condition=%s OpCode=%s, RN=%d RD=%d rotateConstant=%s)", getBinaryString(), condition, opCode, RN, RD, rotateConstant);
    }
}
