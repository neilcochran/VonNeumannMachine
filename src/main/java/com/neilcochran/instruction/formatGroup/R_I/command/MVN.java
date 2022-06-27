package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.Register;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.util.CommandUtil;

public class MVN extends Command {

    private final RegisterFile registerFile;

    public MVN(OpCodeInstruction instruction, RegisterFile registerFile) {
        super(instruction);
        this.registerFile = registerFile;
    }

    @Override
    public void executeCommand() {
        OpCodeInstruction mvnInstruction = (OpCodeInstruction) instruction;
        //TODO enforce register Rd restrictions (mostly around PC/LR)
        Register RD = registerFile.getRegister(mvnInstruction.getRD());
        //negate (~) operand2
        int operand2 = ~CommandUtil.getOpCodeInstructionOperand2(mvnInstruction, registerFile);
        RD.setData(operand2);
        //if S flag set, update N & Z flags (C flag may have been set during barrel shift)
        if(mvnInstruction.getStateFlagBit() == 1) {
            ALU.setNZFlags(operand2, registerFile.getPSR());
        }
    }
}
