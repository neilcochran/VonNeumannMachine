package com.neilcochran.instruction.formatGroup.R.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.formatGroup.R.InstructionR;

public class CMP extends Command {
    private final RegisterFile registerFile;

    public CMP(Instruction instruction, RegisterFile registerFile) {
        super(instruction);
        this.registerFile = registerFile;
    }

    @Override
    public void executeCommand() {
        InstructionR cpmInstruction = (InstructionR) instruction;
        int operand2 = ALU.calculateBarrelShift(cpmInstruction);
    }
}
