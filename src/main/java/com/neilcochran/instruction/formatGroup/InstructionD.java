package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.fields.InstructionFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a D-Instruction
 */
@Getter
@Setter
public class InstructionD extends LoadStoreInstruction {

    /**
     * Takes in an D-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid D-Instruction
     */
    public InstructionD(long instruction) {
        super(instruction, InstructionFormat.D);
    }
}
