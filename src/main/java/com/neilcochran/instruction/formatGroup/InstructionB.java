package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.Instruction;

/**
 * Represents a B-Instruction
 */
public class InstructionB extends Instruction {

    /**
     * Takes in an B-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid B-Instruction
     */
    public InstructionB(int instruction) {
        super(instruction, InstructionFormat.B);
    }
}
