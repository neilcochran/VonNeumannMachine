package com.neilcochran.instruction.formatGroup.R.command;

import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.formatGroup.R.InstructionR;

public class MOV extends Command {

    private final RegisterFile registerFile;

    public MOV(InstructionR instruction, RegisterFile registerFile) {
        super(instruction);
        this.registerFile = registerFile;
    }

    @Override
    public void executeCommand() {
        InstructionR movInstruction = (InstructionR) instruction;
    }
}
