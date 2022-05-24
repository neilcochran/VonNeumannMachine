package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.Instruction;

/**
 * Represents an R-Instruction
 */
public class InstructionR extends Instruction {

    /**
     * Takes in an R-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid R-Instruction
     */
    public InstructionR(int instruction) {
        super(instruction,  InstructionFormat.R);
    }
}
