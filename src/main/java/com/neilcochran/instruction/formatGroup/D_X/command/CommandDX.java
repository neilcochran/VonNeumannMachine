package com.neilcochran.instruction.formatGroup.D_X.command;

import com.neilcochran.component.ControlUnit;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.Instruction;

public abstract class CommandDX extends Command {

    protected final ControlUnit controlUnit;

    public CommandDX(Instruction instruction, RegisterFile registerFile, ControlUnit controlUnit) {
        super(instruction, registerFile);
        this.controlUnit = controlUnit;
    }

    protected int calculateOperand2() {
        //TODO
        return switch (instruction.getInstructionFormat()) {
            case X -> -1;
            case D -> -1;
            default -> throw new IllegalArgumentException("Invalid instruction format: " + instruction.getInstructionFormat());
        };
    }
}
