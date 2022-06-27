package com.neilcochran.instruction.formatGroup.R.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.formatGroup.R.InstructionR;

public class CMP extends Command {

    private final RegisterFile registerFile;

    public CMP(InstructionR instruction, RegisterFile registerFile) {
        super(instruction);
        this.registerFile = registerFile;
    }

    @Override
    public void executeCommand() {
        InstructionR cpmInstruction = (InstructionR) instruction;
        //get the value held in the register indicated by RN
        int operand1 = registerFile.getRegister(cpmInstruction.getRN()).getData();
        //perform barrel shift for operand2
        int operand2 = ALU.calculateBarrelShift(cpmInstruction, registerFile);
        //perform subtraction so PSR flags are set, but discard the result
        ALU.subtract(operand1, operand2, registerFile.getPSR());
    }
}
