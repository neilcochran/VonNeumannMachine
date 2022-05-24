package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.fields.InstructionFormat;

/**
 * Represents an I-Instruction
 */
public class InstructionI extends Instruction {

    /**
     * Takes in an I-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid I-Instruction
     */
    public InstructionI(int instruction) {
        super(instruction, InstructionFormat.I);
    }
}
