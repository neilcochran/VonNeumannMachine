package com.neilcochran.instruction.formatGroup.R.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.formatGroup.R.InstructionR;

public class CMN extends Command {

    private final RegisterFile registerFile;

    public CMN(InstructionR instruction, RegisterFile registerFile) {
        super(instruction);
        this.registerFile = registerFile;
    }

    @Override
    public void executeCommand() {
        InstructionR cmnInstruction = (InstructionR) instruction;
        //get the value hel`d in the register indicated by RN
        int operand1 = registerFile.getRegister(cmnInstruction.getRN()).getData();
        //perform barrel shift for operand2
        int operand2 = ALU.calculateBarrelShift(cmnInstruction, registerFile);
        //perform addition so PSR flags are set, but discard the result
        ALU.add(operand1, operand2, registerFile.getPSR());
    }
}
