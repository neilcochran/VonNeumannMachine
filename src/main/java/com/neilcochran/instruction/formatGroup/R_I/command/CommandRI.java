package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ControlUnit;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.formatGroup.R_I.InstructionI;
import com.neilcochran.instruction.formatGroup.R_I.InstructionR;

/**
 * An abstract class representing a common R & I format group Command
 */
public abstract class CommandRI extends Command {

    /**
     * Constructs a new CommandRI from an `OpCodeInstruction` with references to the needed machine components
     * @param instruction The `OpCodeInstruction` to construct the command from
     * @param registerFile A reference to the machine's `RegisterFile`
     */
    public CommandRI(OpCodeInstruction instruction, RegisterFile registerFile) {
        super(instruction, registerFile);
    }

    /**
     * Calculate the "flexible second operand"
     * @return the calculated second operand value
     */
    protected int calculateOperand2() {
        return switch (instruction.getInstructionFormat()) {
            case R -> ControlUnit.calculateBarrelShift(((InstructionR) instruction), registerFile);
            case I ->  ((InstructionI) instruction).getRotateConstant().getResult();
            default -> throw new IllegalArgumentException("Invalid instruction format: " + instruction.getInstructionFormat());
        };
    }
}
