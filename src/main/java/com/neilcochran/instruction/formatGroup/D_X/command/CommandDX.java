package com.neilcochran.instruction.formatGroup.D_X.command;

import com.neilcochran.component.ControlUnit;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.formatGroup.D_X.InstructionD;
import com.neilcochran.instruction.formatGroup.D_X.InstructionX;
import com.neilcochran.util.BitUtils;

/**
 * An abstract class representing a common D and X format group Command
 */
public abstract class CommandDX extends Command {

    /**
     * A reference to the machine's ControlUnit
     */
    protected final ControlUnit controlUnit;

    /**
     * Constructs a new CommandDX from a LoadStoreInstruction with references to the needed machine components
     * @param instruction The LoadStoreInstruction to construct the command from
     * @param registerFile A reference to the machine's RegisterFile
     * @param controlUnit A reference to the machine's ControlUnit
     */
    public CommandDX(LoadStoreInstruction instruction, RegisterFile registerFile, ControlUnit controlUnit) {
        super(instruction, registerFile);
        this.controlUnit = controlUnit;
    }

    /**
     * Calculate the "flexible second operand"
     * @return the calculated second operand value
     */
    protected int calculateOperand2() {
        LoadStoreInstruction loadStoreInstruction = (LoadStoreInstruction) instruction;
        //get the initial value for operand2. After we will check to see if we are further modifying it (as an offset)
        int operand2;
        switch (loadStoreInstruction.getInstructionFormat()) {
            case X -> operand2 = ControlUnit.calculateBarrelShift((InstructionX) loadStoreInstruction, registerFile);
            case D -> operand2 = ((InstructionD) instruction).getRotateConstant().getResult();
            default -> throw new IllegalArgumentException("Invalid instruction format: " + instruction.getInstructionFormat());
        }
        var RN = registerFile.getRegister(((LoadStoreInstruction) instruction).getRN());
        //check if we are adding the offset (U==1) from RN or subtracting it (U==0)
        return BitUtils.bitToBool(loadStoreInstruction.getOffsetAddSubBit())
                ? operand2 + RN.getData()
                : operand2 - RN.getData();
    }
}
