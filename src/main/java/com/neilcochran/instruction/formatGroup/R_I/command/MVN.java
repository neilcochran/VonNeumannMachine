package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.OpCodeInstruction;

public class MVN extends CommandRI {

    private final ProgramStatusRegister PSR;

    public MVN(OpCodeInstruction instruction, RegisterFile registerFile, ProgramStatusRegister PSR) {
        super(instruction, registerFile);
        this.PSR = PSR;
    }

    @Override
    public void executeCommand() {
        var mvnInstruction = (OpCodeInstruction) instruction;
        //TODO enforce register Rd restrictions (mostly around PC/LR)
        var RD = registerFile.getRegister(mvnInstruction.getRD());
        //negate (~) operand2
        var operand2 = ~calculateOperand2();
        RD.setData(operand2);
        //if S flag set, update N & Z flags (C flag may have been set during barrel shift)
        if(mvnInstruction.getStateFlagBit() == 1) {
            ALU.setNZFlags(operand2, PSR);
        }
    }
}
