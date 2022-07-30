package com.neilcochran.instruction.formatGroup.B;

import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Instruction of the B format group
 */
@Getter
@Setter
public class InstructionB extends Instruction {

    /**
     * The bit index of the link register flag which denotes if the current program counter value should be saved in the link register (LR)
     * @see com.neilcochran.instruction.formatGroup.B.InstructionB#linkRegisterFlagBit
     */
    private static final int L_FLAG_INDEX = 24;

    /**
     * The bit range of the 12 bit immediate constant value which will be processed into a 24 bit immediate constant
     * @see com.neilcochran.instruction.formatGroup.B.InstructionB#imm24
     */
    private static final BitRange IMM12_RANGE = new BitRange(0, 11);

    private final int linkRegisterFlagBit;
    private final int imm24;

    /**
     * Constructs an InstructionB from an integer that encodes for an instruction of the B format group
     * @param instruction The integer that encodes for the given InstructionB
     * @throws IllegalArgumentException if the instruction is not a valid InstructionB
     */
    public InstructionB(int instruction) {
        super(instruction, InstructionFormat.B);
        linkRegisterFlagBit = BitUtils.getKthBit(instruction, L_FLAG_INDEX);
        //When this value is used it will be right shifted by 2 (so multiplied by 4) and then added to the PC
        //This means that although it is a 24bit constant is will be used as a 26bit constant (where the 2 LSBs are always 0 due to our right shifts)
        imm24 = BitUtils.getBitRange(instruction, IMM12_RANGE);
    }

    /**
     * Get a string representation of the InstructionB instance
     * @return A string representation of the InstructionB instance
     */
    @Override
    public String toString() {
        return String.format("InstructionB(instruction=%s condition=%s linkRegisterFlagBit=%d, imm24=%d)", getBinaryString(), condition, linkRegisterFlagBit, imm24);
    }
}
