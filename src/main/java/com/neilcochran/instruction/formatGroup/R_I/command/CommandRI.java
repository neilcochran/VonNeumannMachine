package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ControlUnit;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.formatGroup.R_I.InstructionI;
import com.neilcochran.instruction.formatGroup.R_I.InstructionR;

public abstract class CommandRI extends Command {

    public CommandRI(OpCodeInstruction instruction, RegisterFile registerFile) {
        super(instruction, registerFile);
    }

    protected int calculateOperand2() {
        return switch (instruction.getInstructionFormat()) {
            case R -> ControlUnit.calculateBarrelShift(((InstructionR) instruction), registerFile);
            case I ->  ((InstructionI) instruction).getRotateConstant().getResult();
            default -> throw new IllegalArgumentException("Invalid instruction format: " + instruction.getInstructionFormat());
        };
    }
}
