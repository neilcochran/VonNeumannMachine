package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.fields.InstructionFormat;

public class InstructionB extends Instruction {
    public InstructionB(long instruction) {
        super(instruction, InstructionFormat.B);
    }
}
