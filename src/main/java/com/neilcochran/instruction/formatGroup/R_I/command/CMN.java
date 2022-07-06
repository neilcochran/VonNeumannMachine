package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.OpCodeInstruction;

public class CMN extends CommandRI {

    private final ProgramStatusRegister PSR;

    public CMN(OpCodeInstruction instruction, RegisterFile registerFile, ProgramStatusRegister PSR) {
        super(instruction, registerFile);
        this.PSR = PSR;
    }

    @Override
    public void executeCommand() {
        OpCodeInstruction cmnInstruction = (OpCodeInstruction) instruction;
        var operand1 = registerFile.getRegister(cmnInstruction.getRN()).getData();
        var operand2 = calculateOperand2();
        //perform addition so PSR flags are set, but discard the result
        ALU.add(operand1, operand2, PSR);
    }
}
