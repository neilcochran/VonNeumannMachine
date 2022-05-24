package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.Instruction;

/**
 * Represents an X-Instruction
 */
public class InstructionX extends Instruction {

    /**
     * Takes in an X-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid X-Instruction
     */
    public InstructionX(int instruction) {
        super(instruction, InstructionFormat.X);
    }
}
