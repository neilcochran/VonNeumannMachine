package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.Instruction;

/**
 * Represents a D-Instruction
 */
public class InstructionD extends Instruction {

    /**
     * Takes in an D-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid D-Instruction
     */
    public InstructionD(int instruction) {
        super(instruction, InstructionFormat.D);
    }
}
