package com.neilcochran.instruction.formatGroup.B.command;

import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.formatGroup.B.InstructionB;

public class Branch implements Command {
    @Override
    public void executeCommand(Instruction branchInstruction) {
        if(branchInstruction instanceof InstructionB) {
            System.out.println("Executing branch instruction: " + branchInstruction);
        }
        else {
            throw new IllegalArgumentException("instruction must be an instance of InstructionB");
        }
    }
}
