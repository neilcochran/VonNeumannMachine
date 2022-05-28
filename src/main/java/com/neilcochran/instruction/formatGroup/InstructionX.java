package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.fields.InstructionFormat;

/**
 * Represents an X-Instruction
 */
public class InstructionX extends LoadStoreInstruction {

    /**
     * Takes in an X-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid X-Instruction
     */
    public InstructionX(long instruction) {
        super(instruction, InstructionFormat.X);
    }
}
